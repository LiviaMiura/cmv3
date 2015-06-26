package br.faetec.hardware.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Implementa os métodos aquisicao de dados do hardware, via porta paralela.
@author Antonio Cassiano
 **/
public class AquisicaoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    short sincronismo = 0;
    short sinalPPS = 0;
    int i = 0;
    int numeroLeiturasArquivo;
    private boolean stop;
    private int segundos;
    private int minutos;
    private int horas;
    private long numeroPosicao3, numeroPosicao2, numeroPosicao1, numeroPosicao0 = 0;
    private long numeroPosicaoFino0 = 0;
    //Tamanho do Vetor de medidas ########################################
    int tamanho = 7200;  // duas horas de teste
    int contadorGrupo = 1;  // valor inicial do contador de grupo
    int numeroLeitura = 12; // numero de leituras = 10 + 2 leituras desprezadas no cálculo
    int numeroGrupo = 1;
    int janelaMedida = 1;
    //
    String nomeArquivo;
    String dadosArquivoLido[] = new String[tamanho];
    String dadosArquivoLidoProcessado[] = new String[tamanho];
    String datacao[] = new String[tamanho];
    String iLido[] = new String[tamanho];
    String contadorGrupoLido[] = new String[tamanho];
    long contadorTotalizadorHW[] = new long[tamanho];
    long contadorFinoHW[] = new long[tamanho];
    long contadorTotalizadorLido[] = new long[tamanho];
    long contadorFinoLido[] = new long[tamanho];
    String datacaoLido[] = new String[tamanho];
    long deltaContadorTotalizador[] = new long[tamanho];
    long deltaContadorFino[] = new long[tamanho];
    long ajusteFino[] = new long[tamanho];
    double frequencia[] = new double[tamanho];
    double frequencia_aux = 0;
    long statusHW = 0;
    long statusSIS = 0;
    double media[] = new double[tamanho];
    double frequenciaReferencia = 180000000.00;
    AcessoParalelaAction portaParalela = new AcessoParalelaAction();

    /**
    Leitura do hardware atraves da thread invocando o metodo aquisicao,
    Sincronismo com PPS, leituras do HW, processaHW, calculos, apresentação do resultado
    e montagem do arquivo; Leitura do arquivo, preparaProcessaArquivos calculos e apresentacao dos resultados.
    @author Antonio Cassiano
     **/
    public AquisicaoAction() {
    } //fim do construtor 

    /**
     *Verifica status do hardware e dispara Thread para leitura do hardware.
    @author Antonio Cassiano
    @param numeroGrupoSelecionado
    @param testeJanelaMedida
    @param nomeArquivoSelecionado
     **/
    public void lerHardware(int numeroGrupoSelecionado, int testeJanelaMedida, String nomeArquivoSelecionado) {

        numeroGrupo = numeroGrupoSelecionado;  // atualiza numerode Grupo
        janelaMedida = testeJanelaMedida;
        nomeArquivo = nomeArquivoSelecionado;
        stop = false;

        leituraStatusHw();

        new Relogio().start();         // start thread
    }

    /**
     *Thread relogio que dispara a aquisicao do hardware.
    @author Antonio Cassiano
     **/
    public class Relogio extends Thread {

        @Override
        public void run() {
            try {
                aquisicao(numeroGrupo, janelaMedida, nomeArquivo);
            } catch (IOException ex) {
                Logger.getLogger(AquisicaoAction.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
    Aquisicao de dados do hw com verificacao do bit PPS do Hardware, aguarde mudar para NL1;
    Efetua acesso ao relogio, contadores, formata saida para tela, gera sinal reset sinal PPS para nova aquisicao,
    e monta arquivo de dados ao final da aquisicao
    @author Antonio Cassiano
    @param numeroGrupo
    @param janelaMedida
    @param nomeArquivo
    @throws IOException
     **/
    public void aquisicao(int numeroGrupo, int janelaMedida, String nomeArquivo) throws IOException {
        contadorGrupo = 1;
        numeroLeitura = 12;
        i = 0;
        if (janelaMedida == 10) {
            numeroGrupo = numeroGrupo * 10;                     // janelaMedida de 10s
        }

        System.out.println("\nNúmero de grupos na aquisicao : " + numeroGrupo + "   JM  : " + janelaMedida);

        int iaux = 0;
        resetPPS();
        boolean flagArquivo = false;        //arquivo vazio
        System.out.println("Inicio das leituras");
        do {                                                                         //contadorGrupo < numeroGrupo e stop = false
            do {                                                                     //iaux < numeroLeitura
                while (sinalPPS == 0) {
                    sinalPPS = Sincronismo();        //aguarda sincronismo assumir NL1
                }
                leituras();
                if (contadorGrupo > 1) {                  //numero de leituras = 12, apenas na primeira vez
                    numeroLeitura = 10;
                }

                if (janelaMedida == 10) {
                    processaHWJanela10();
                } else {
                    processaHW();
                }

                sinalPPS = 0;
                i++;
                iaux++;
                resetPPS();
            } while (iaux < numeroLeitura);

            contadorGrupo++;
            iaux = 0;                                                                   //iaux = 0, para leitura sequencial do próximo grupo

            if (janelaMedida == 1) {
                System.out.println("Grupo = " + (contadorGrupo - 1));

            } else {
                if ((contadorGrupo - 1) % 10 == 0) {
                    System.out.println("Grupo = " + ((contadorGrupo - 1) / 10));

                }
            }

        } while (contadorGrupo < (numeroGrupo + 1));

        montarArquivoCalibracao(i, flagArquivo, nomeArquivo);     //medir tempo com "DAS" ####################
        System.out.println("Fim das leituras," + (contadorGrupo - 1) + " grupos e Geração do Arquivo  " + nomeArquivo + " ok !");

    }

    /**
    Leitura do bit correspondente ao PPS
    @author Antonio Cassiano
    @return sincronismo
     **/
    public short Sincronismo() {
        portaParalela.Addr = 0x379;
        sincronismo = portaParalela.readData();
        sincronismo = (short) ((short) (sincronismo >> 3) & 0x01);  //bit 3
        return sincronismo;
    }

    /**
    Comando para reset do registro de HW que recebe o sinal PPS.
    @author Antonio Cassiano
     **/
    public void resetPPS() {
        portaParalela.Addr = 0x378;   // bit CS7, 0X378
        portaParalela.datum = 0xFF;
        portaParalela.writeData();
        portaParalela.datum = 0x7F;
        portaParalela.writeData();
        portaParalela.datum = 0xFF;
        portaParalela.writeData();

    }

    /**
    Envio de comando para porta 37Ah, geracao do Chip Select para o registro permanente de dados
    @author Antonio Cassiano
     **/
    public void envioComando() {

        portaParalela.Addr = 0x37A;   // bit 0, NL1, 0X37A
        portaParalela.datum = 0x0E;   // CUIDADO COM OS OUTROS BITS
        portaParalela.writeData();
        portaParalela.datum = 0x0F;
        portaParalela.writeData();
        portaParalela.datum = 0x0E;
        portaParalela.writeData();
    }

    /**
    Invoca  todos os metodos de leitura do hardware.
    @author Antonio Cassiano
     **/
    public void leituras() {
        leituraRelogio();
        leituraContador();
        leituraContadorFino();
        // leituraStatusHw();
        formatacaoLeitura();// apresenta a datacao formatada hh:mm:ss
    }

    /**
    Método para acesso a datacao de dados do hardware; Faixa de Endereco: 0x80h ate 0x85h,
    Dezena e Unidade de horas, Dezena e Unidade de Minutos e Dezenas e Unidades de Segundos
    @author Antonio Cassiano
     **/
    public void leituraRelogio() {

        portaParalela.Addr = 0x378;  //unidade de segundos
        portaParalela.datum = 0x80;
        portaParalela.writeData();

        portaParalela.Addr = 0x379;
        short unidadeSegundos = portaParalela.readData();
        unidadeSegundos = (short) ((short) (unidadeSegundos >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x81;  //dezena de segundos
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short dezenaSegundos = portaParalela.readData();
        dezenaSegundos = (short) (dezenaSegundos & 0x00F0);
        segundos = (dezenaSegundos | unidadeSegundos);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x82;   //endereca unidades minutos
        portaParalela.writeData();

        portaParalela.Addr = 0x379;
        short unidadeMinutos = portaParalela.readData();
        unidadeMinutos = (short) ((short) (unidadeMinutos >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x83;  //endereca dezena de minutos
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short dezenaMinutos = portaParalela.readData();
        dezenaMinutos = (short) (dezenaMinutos & 0x00F0);
        minutos = (short) (dezenaMinutos | unidadeMinutos);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x84;   //endereca unidades horas
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short unidadeHoras = portaParalela.readData();
        unidadeHoras = (short) ((short) (unidadeHoras >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x85;  //endereca dezena de horas
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short dezenaHoras = portaParalela.readData();
        dezenaHoras = (short) (dezenaHoras & 0x00F0);
        horas = (short) ((dezenaHoras | unidadeHoras));



    }

    /**
    Método para acesso ao contador Totalizador do hardware no vetor contadorTotalizadorHW[i];
    Faixa de Endereco: 0x90h ate 0x97h,  
    @author Antonio Cassiano
     **/
    public void leituraContador() {

        //posicao dos bits
        //D7 D6 D5 D4 X X X X
        //numeroTotal, mumeroPosicao3,numeroPosicao2,numeroPosicao1,numeroPosicao0;

        portaParalela.Addr = 0x378;  //C3..C0
        portaParalela.datum = 0x90;
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C3_C0 = portaParalela.readData();
        C3_C0 = (short) ((short) (C3_C0 >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x91;  //C7_C4
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C7_C4 = portaParalela.readData();
        C7_C4 = (short) (C7_C4 & 0x00F0);

        numeroPosicao0 = (C7_C4 | C3_C0);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x92;  //C11_C8
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C11_C8 = portaParalela.readData();
        C11_C8 = (short) ((short) (C11_C8 >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x93;  //C15_C12
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C15_C12 = portaParalela.readData();
        C15_C12 = (short) (C15_C12 & 0x00F0);


        numeroPosicao1 = (C15_C12 | C11_C8);
        numeroPosicao1 = ((numeroPosicao1) << 8 & 0xFF00);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x94;  //C19_C16
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C19_C16 = portaParalela.readData();
        C19_C16 = (short) ((short) (C19_C16 >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x95;  //C23_C20
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C23_C20 = portaParalela.readData();
        C23_C20 = (short) (C23_C20 & 0x00F0);

        numeroPosicao2 = (C23_C20 | C19_C16);
        numeroPosicao2 = ((numeroPosicao2) << 16 & 0xFF0000);


        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x96;  //C27_C24
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C27_C24 = portaParalela.readData();
        C27_C24 = (short) ((short) (C27_C24 >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0x97;  //C31_C28
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C31_C28 = portaParalela.readData();
        C31_C28 = (short) (C31_C28 & 0x00F0);

        numeroPosicao3 = (C31_C28 | C27_C24);
        numeroPosicao3 = ((numeroPosicao3) << 24 & 0xFF000000);

        contadorTotalizadorHW[i] = (numeroPosicao3 | numeroPosicao2 | numeroPosicao1 | numeroPosicao0);

    }

    /**
    Método para acesso ao contador de ajuste do hardware no vetor contadorFinoHW[i];
    Faixa de Endereco: 0xA0h ate 0xA1h,
    @author Antonio Cassiano
     **/
    public void leituraContadorFino() {

        //posicao dos bits
        //D7 D6 D5 D4 X X X X
        //numeroTotal, mumeroPosicao3,numeroPosicao2,numeroPosicao1,numeroPosicao0;

        portaParalela.Addr = 0x378;  //C3..C0
        portaParalela.datum = 0xA0;  // era 90 viu Cassiano
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C3_C0 = portaParalela.readData();
        C3_C0 = (short) ((short) (C3_C0 >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0xA1;  //C7_C4
        portaParalela.writeData(); // era 91 viu Cassiano
        portaParalela.Addr = 0x379;
        short C7_C4 = portaParalela.readData();
        C7_C4 = (short) (C7_C4 & 0x00F0);

        numeroPosicaoFino0 = (C7_C4 | C3_C0);

        contadorFinoHW[i] = numeroPosicaoFino0;


    }

    /**
    Acesso ao status do hardware;
    Faixa de Endereco: 0xB0h ate 0xB1h,
    @author Antonio Cassiano
    @return statusHW
     **/
    public long leituraStatusHw() {

        //posicao dos bits
        //D7 D6 D5 D4 X X X X
        //numeroTotal, mumeroPosicao3,numeroPosicao2,numeroPosicao1,numeroPosicao0;

        portaParalela.Addr = 0x378;  //C3..C0
        portaParalela.datum = 0xB0;
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C3_C0 = portaParalela.readData();
        C3_C0 = (short) ((short) (C3_C0 >> 4) & 0x000F);

        portaParalela.Addr = 0x378;
        portaParalela.datum = 0xB1;  //C7_C4
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C7_C4 = portaParalela.readData();
        C7_C4 = (short) (C7_C4 & 0x00F0);

        statusHW = (C7_C4 | C3_C0);

        System.out.println("\nStatus HW :" + statusHW);
        return statusHW;
    }

    /**
    Acesso ao status do sistema;
    Endereco: 0xA2h,
    @author Antonio Cassiano
    @return statusSIS
     **/
    public long leituraStatusSistema() {
        //posicao dos bits
        //D7 D6 D5 D4 X X X X   
        portaParalela.Addr = 0x378;  //C3..C0
        portaParalela.datum = 0xA2;
        portaParalela.writeData();
        portaParalela.Addr = 0x379;
        short C3_C0 = portaParalela.readData();
        C3_C0 = (short) ((short) (C3_C0 >> 4) & 0x0001);
        statusSIS = C3_C0;
        System.out.println("\nStatus SISTEMA :" + statusSIS);
        return statusSIS;
    }

    /**
    Envio de comando de start software de medida tipo 1;
    Endereco: 0xC0h,
    @author Antonio Cassiano
     **/
    public void startSWold() {
        portaParalela.Addr = 0x378;
        portaParalela.datum = 0xC0;  // efetiva start = 1
        portaParalela.writeData();
        System.out.println("\nStart SW ######################################################################################### ");
    }

    /**
    Envio de comando de stop software de medida tipo 1;
    Endereco: 0xC2h,
    @author Antonio Cassiano
     **/
    public void stopSWold() {
        portaParalela.Addr = 0x378;
        portaParalela.datum = 0xC2;  //envia zero
        portaParalela.writeData();
        System.out.println("\nStop SW ********************************************************** ");
    }

    /**
    Envio de comando de reset sistema;
    Endereco: 0xD0h,
    @author Antonio Cassiano
     **/
    public void resetSistema() {
        portaParalela.Addr = 0x378;
        portaParalela.datum = 0xD0;  // efetiva RESET = 0
        portaParalela.writeData();
        System.out.println("\nReset Sistema ");
    }

    /**
    Envio de comando de start software de medida;
    @author Antonio Cassiano
     **/
    public void startSW() {
        portaParalela.Addr = 0x378;
        portaParalela.writeData2((short) 0xFF);

        portaParalela.Addr = 0x37A;
        portaParalela.datum = 0x02;  // habilita CS1 para registrar o dado apenas 1 vez.
        portaParalela.writeData();

        portaParalela.datum = 0x0F;  //inibe CS1, não usar CS7
        portaParalela.writeData();
        System.out.println("\nStart SW ######################################################################################### ");
    }

    /**
    Envio de comando de stop software de medida;
    @author Antonio Cassiano
     **/
    public void stopSW() {
        portaParalela.Addr = 0x378;
        portaParalela.writeData2((short) 0x00);

        portaParalela.Addr = 0x37A;
        portaParalela.datum = 0x02;  // habilita CS1 para registrar o dado apenas 1 vez.
        portaParalela.writeData();
        portaParalela.datum = 0x0F;  //inibe CS1, não usar CS7
        portaParalela.writeData();
        System.out.println("\nStop SW ********************************************************** ");

    }

    /**
    Formatacao da datacao, contador totalizador e contador de intervalo
    @author Antonio Cassiano
     **/
    public void formatacaoLeitura() {
        DecimalFormat quatroDigitos = new DecimalFormat("####");
        quatroDigitos.setMinimumIntegerDigits(4);
        String dx = quatroDigitos.format(i);

        DecimalFormat doisDigitos = new DecimalFormat("##");
        doisDigitos.setMinimumIntegerDigits(2);


        String hexaSegundos = Integer.toString(segundos, 16);  // convrte para hexadecimal
        int inteiroSegundos = Integer.parseInt(hexaSegundos);  // converte de String  para inteiro
        String segundosFormatado = doisDigitos.format(inteiroSegundos);

        String hexaMinutos = Integer.toString(minutos, 16);  // convrte para hexadecimal
        int inteiroMinutos = Integer.parseInt(hexaMinutos);  // converte de String  para inteiro
        String minutosFormatado = doisDigitos.format(inteiroMinutos);

        String hexaHoras = Integer.toString(horas, 16);  // convrte para hexadecimal
        int inteiroHoras = Integer.parseInt(hexaHoras);  // converte de String  para inteiro
        String horasFormatado = doisDigitos.format(inteiroHoras);

        datacao[i] = (horasFormatado + ":" + minutosFormatado + ":" + (segundosFormatado));

    }

    /**
    Montagem do arquivo calibracaoCMV.txt
    @author Antonio Cassiano
    @param leituras
    @param  flagArquivo
    @param nomeArquivo
     **/
    public void montarArquivoCalibracao(int leituras, boolean flagArquivo, String nomeArquivo) throws IOException {

        // "arquivo/arquivoCMVCalibracao.txt"

        String arquivoCalibracao = session.getServletContext().getRealPath("/") + nomeArquivo;
        FileWriter arquivoX = new FileWriter(arquivoCalibracao, flagArquivo);

        for (i = 0; i < leituras; i++) {
            Object valor0 = i;
            Object valor1 = contadorGrupo;
            Object valor2 = datacao[i];
            Object valor3 = contadorTotalizadorHW[i];
            Object valor4 = contadorFinoHW[i];
            try {
                arquivoX.write(valor0 + " ");
                arquivoX.write(valor1 + " ");
                arquivoX.write(valor2 + " ");
                arquivoX.write(valor3 + " ");
                arquivoX.write(valor4 + "\n");
            } catch (Exception e) {
                System.out.println("Erro na criação do arquivo " + nomeArquivo);
            }
        }
        arquivoX.close();
    }

    /**
    Leitura e processamento do arquivo gerado
    @author Antonio Cassiano
    @param numeroGrupo
    @param janelaMedida
    @throws IOException
     **/
    public void preparaProcessaArquivo(int numeroGrupo, int janelaMedida) throws IOException {
        contadorGrupo = 1;
        numeroLeitura = 12;
        i = 0;
        int iaux = 0;
        int offset = 0;
        if (janelaMedida == 10) {
            numeroGrupo = numeroGrupo * 10;                         // janelaMedida de 10s
        }

        do {                                                                                                //contadorGrupo < numeroGrupo + 1
            do {                                                                                            //iaux < numeroLeitura
                String dados = dadosArquivoLido[i];       //separacao, por espaço em branco, da string dadosArquivoLido
                String[] dado = dados.split("\\ ");      //
                iLido[i] = dado[0];
                contadorGrupoLido[i] = dado[1];
                datacaoLido[i] = dado[2];
                contadorTotalizadorLido[i] = Long.parseLong(dado[3]);
                contadorFinoLido[i] = Long.parseLong(dado[4]);

                if (contadorGrupo > 1) {                  //offset para indice de varredura do arquivo
                    numeroLeitura = 10;
                }
                offset = (numeroLeitura * (contadorGrupo - 1));

                if (janelaMedida == 10) {
                    processaArquivoJanela10(offset, janelaMedida);
                } else {
                    processaArquivo(offset, janelaMedida);
                }
                i++;
                iaux++;
            } while (iaux < numeroLeitura);             // loop numeroLeitura para formação dos grupos
            contadorGrupo++;
            iaux = 0;

            if (janelaMedida == 1) {
                System.out.println("Grupo = " + (contadorGrupo - 1) + "  offset = " + offset);

            } else {
                if ((contadorGrupo - 1) % 10 == 0) {
                    System.out.println("Grupo = " + ((contadorGrupo - 1) / 10));
                }
            }
        } while (contadorGrupo < (numeroGrupo + 1));

        //local 23/04/2011  AQUI CASSIANO !!!!!
        if (janelaMedida == 1) {
            montarArquivoProcessado(i, false);
        } else {
            montarArquivoProcessado10(i, false);
        }
    }

    /**
    Processa arquivo de dados, efetua calculos de frequencia para janela de 1s
    @author Antonio Cassiano
    @param offset
    @param janelaMedida
    @throws IOException
     **/
    public void processaArquivo(int offset, int janelaMedida) throws IOException {

        if (i > 0 + offset) {
            deltaContadorTotalizador[i] = (contadorTotalizadorLido[i] - contadorTotalizadorLido[i - 1]);

            if (contadorFinoLido[i] < contadorFinoLido[i - 1]) {
                deltaContadorFino[i] = (contadorFinoLido[i] - contadorFinoLido[i - 1]) + 256;
            } else {
                deltaContadorFino[i] = (contadorFinoLido[i] - contadorFinoLido[i - 1]);
            }
            ajusteFino[i] = (deltaContadorFino[i] - deltaContadorFino[i - 1]);

            frequencia[i] = (double) (deltaContadorTotalizador[i] / (1 + (ajusteFino[i] / frequenciaReferencia)));

            // apresenta calculo valido

            if (i > (1 + offset)) {
                apresentaCalculosArquivo(offset, janelaMedida);
            }
        }
    }

    /**
    Formata e apresenta calculos do Arquivo
    @author Antonio Cassiano
    @param offset
    @param janelaMedida
    @throws IOException
     **/
    public void apresentaCalculosArquivo(int offset, int janelaMedida) throws IOException {
        int contadorGrupo_aux;

        DecimalFormat quatroDigitos = new DecimalFormat("####");
        quatroDigitos.setMinimumIntegerDigits(4);
        String ix = quatroDigitos.format((i - 2) - offset);  // reposiciona para mostrar valor válido

        //       System.out.println("Janela Medida apresenta Calculos :"+ janelaMedida);

        if (janelaMedida == 10) {
            contadorGrupo_aux = (int) ((((contadorGrupo - 1) / 10)) + 1);
            ix = quatroDigitos.format(i);
        } else {
            contadorGrupo_aux = contadorGrupo;
        }

        DecimalFormat oitoDigitos = new DecimalFormat("#,###,###,###");
        oitoDigitos.setMinimumIntegerDigits(8);
        String totalizador = oitoDigitos.format(contadorTotalizadorLido[i]);

        DecimalFormat tresDigitos = new DecimalFormat("###");
        tresDigitos.setMinimumIntegerDigits(3);
        String fino = tresDigitos.format(contadorFinoLido[i]);

        DecimalFormat seteDigitos = new DecimalFormat("#,###,###");
        seteDigitos.setMinimumIntegerDigits(7);
        String deltaTotalizador = seteDigitos.format(deltaContadorTotalizador[i]);

        String deltaFino = tresDigitos.format(deltaContadorFino[i]);

        DecimalFormat dezDigitos = new DecimalFormat("#,###,###,##0.000");
        dezDigitos.setMinimumIntegerDigits(7);
        String frequenciaFinal = dezDigitos.format(frequencia[i]);


        System.out.println((ix) + " "
                //+ contadorGrupo + "  "
                + contadorGrupo_aux + "  "
                + datacaoLido[i] + "    "
                + totalizador + "     "
                + fino + "     "
                + deltaTotalizador + "     "
                + deltaFino + "    "
                + frequenciaFinal);


    }

    /**
    Monta e gera Arquivo Processado para 1s
    @author Antonio Cassiano
    @param leituras
    @param flagArquivo
    @throws IOException
     **/
    public void montarArquivoProcessado(int leituras, boolean flagArquivo) throws IOException {
        //   String arquivoProcessado = request.getRealPath("/") + "arquivo/arquivoCMVProcessado.txt";
        String arquivoProcessado = session.getServletContext().getRealPath("/") + "arquivo/arquivoCMVProcessado.txt";
        FileWriter arquivo = new FileWriter(arquivoProcessado, flagArquivo);

        for (i = 0; i < leituras; i++) {
            Object valor0 = i;
            Object valor1 = contadorGrupo;
            Object valor2 = datacaoLido[i];
            Object valor3 = contadorTotalizadorLido[i];
            Object valor4 = contadorFinoLido[i];
            Object valor5 = deltaContadorTotalizador[i];
            Object valor6 = deltaContadorFino[i];
            Object valor7 = frequencia[i];
            try {
                arquivo.write(valor0 + " ");
                arquivo.write(valor1 + " ");
                arquivo.write(valor2 + " ");
                arquivo.write(valor3 + " ");
                arquivo.write(valor4 + " ");
                arquivo.write(valor5 + " ");
                arquivo.write(valor6 + " ");
                arquivo.write(valor7 + "\n");
            } catch (Exception e) {
                System.out.println("Erro na criação do arquivo  arquivoCMVProcessado.txt!!! ");
            }
        }
        arquivo.close();
    }

    /**
    Monta e gera Arquivo Processado para 10s
    @author Antonio Cassiano
    @param leituras
    @param flagArquivo
    @throws IOException
     **/
    public void montarArquivoProcessado10(int leituras, boolean flagArquivo) throws IOException {

//        String arquivoProcessado = request.getRealPath("/") + "arquivo/arquivoCMVProcessado.txt";

        String arquivoProcessado = session.getServletContext().getRealPath("/") + "arquivo/arquivoCMVProcessado.txt";


        FileWriter arquivo = new FileWriter(arquivoProcessado);

        for (i = 1; i < leituras; i = i + 10) {
            Object valor0 = i;
            Object valor1 = contadorGrupo;
            Object valor2 = datacaoLido[i];
            Object valor3 = contadorTotalizadorLido[i];
            Object valor4 = contadorFinoLido[i];
            Object valor5 = deltaContadorTotalizador[i];
            Object valor6 = deltaContadorFino[i];
            Object valor7 = frequencia[i];
            try {
                arquivo.write(valor0 + " ");
                arquivo.write(valor1 + " ");
                arquivo.write(valor2 + " ");
                arquivo.write(valor3 + " ");
                arquivo.write(valor4 + " ");
                arquivo.write(valor5 + " ");
                arquivo.write(valor6 + " ");
                arquivo.write(valor7 + "\n");
            } catch (Exception e) {
                System.out.println("Erro na criação do arquivo  arquivoCMVProcessado.txt!!! ");
            }
        }
        arquivo.close();
    }

    /**
    processa arquivo de dados, efetua calculos de frequencia para Janela de 10s
    @author Antonio Cassiano
    @param offset
    @param  janelaMedida 
    @throws IOException
     **/
    public void processaArquivoJanela10(int offset, int janelaMedida) throws IOException {

        if (i > 0) {
            if (contadorFinoLido[i] < contadorFinoLido[i - 1]) {
                deltaContadorFino[i] = (contadorFinoLido[i] - contadorFinoLido[i - 1]) + 256;
            } else {
                deltaContadorFino[i] = (contadorFinoLido[i] - contadorFinoLido[i - 1]);
            }
        }

        if (i > 10 + offset) {
            deltaContadorTotalizador[i] = (contadorTotalizadorLido[i] - contadorTotalizadorLido[i - 10]);

            if (contadorFinoLido[i - 10] < contadorFinoLido[i - 11]) {
                deltaContadorFino[i - 10] = (contadorFinoLido[i - 10] - contadorFinoLido[i - 11]) + 256;
            } else {
                deltaContadorFino[i - 10] = (contadorFinoLido[i - 10] - contadorFinoLido[i - 11]);
            }

            ajusteFino[i] = (deltaContadorFino[i] - deltaContadorFino[i - 10]);

            frequencia[i] = (double) (deltaContadorTotalizador[i] / (10 + (ajusteFino[i] / frequenciaReferencia)));
            // apresenta calculo valido
            if (i > (10 + offset)) {
                apresentaCalculosArquivo(offset, janelaMedida);
            }
        }
    }

    /**
    processa dados do hardware para cada aquisição, efetua calculos de frequencia.
    @author Antonio Cassiano
     **/
    public void processaHW() {
        if (i > 0) {
            deltaContadorTotalizador[i] = (contadorTotalizadorHW[i] - contadorTotalizadorHW[i - 1]);

            if (contadorFinoHW[i] < contadorFinoHW[i - 1]) {
                deltaContadorFino[i] = (contadorFinoHW[i] - contadorFinoHW[i - 1]) + 256;
            } else {
                deltaContadorFino[i] = (contadorFinoHW[i] - contadorFinoHW[i - 1]);
            }

            //atencao Cassiano janelaMedida =1 08/03/2011
            ajusteFino[i] = (deltaContadorFino[i] - deltaContadorFino[i - 1]);
            frequencia[i] = (double) (deltaContadorTotalizador[i] / (1 + (ajusteFino[i] / frequenciaReferencia)));

            // apresenta calculo valido
            if (i > 1) {
                apresentaCalculosHW();
            }
        }
    }

    /**
    Processa dados do hardware para cada aquisição, efetua calculos de frequencia para janela de 10s.
    @author Antonio Cassiano
     **/
    public void processaHWJanela10() {

        if (i > 0) {
            if (contadorFinoHW[i] < contadorFinoHW[i - 1]) {
                deltaContadorFino[i] = (contadorFinoHW[i] - contadorFinoHW[i - 1]) + 256;
            } else {
                deltaContadorFino[i] = (contadorFinoHW[i] - contadorFinoHW[i - 1]);
            }
        }


        if (i > (10 * (contadorGrupo))) {
            deltaContadorTotalizador[i] = (contadorTotalizadorHW[i] - contadorTotalizadorHW[i - 10]);

            if (contadorFinoHW[i - 10] < contadorFinoHW[i - 11]) {
                deltaContadorFino[i - 10] = (contadorFinoHW[i - 10] - contadorFinoHW[i - 11]) + 256;
            } else {
                deltaContadorFino[i - 10] = (contadorFinoHW[i - 10] - contadorFinoHW[i - 11]);
            }

            ajusteFino[i] = (deltaContadorFino[i] - deltaContadorFino[i - 10]);

            frequencia[i] = (double) (deltaContadorTotalizador[i] / (10 + (ajusteFino[i] / frequenciaReferencia)));


            //apresenta calculo valido
            if (i > (10 * (contadorGrupo))) {
                apresentaCalculosHW();
            }

        }
    }

    /**
    Formata e apresenta calculos do Hardware
    @author Antonio Cassiano
     **/
    public void apresentaCalculosHW() {
        DecimalFormat quatroDigitos = new DecimalFormat("####");
        quatroDigitos.setMinimumIntegerDigits(4);
        String ix = quatroDigitos.format(i - 2);  // reposiciona i para valor valido

        DecimalFormat oitoDigitos = new DecimalFormat("#,###,###,###");
        oitoDigitos.setMinimumIntegerDigits(8);
        String totalizador = oitoDigitos.format(contadorTotalizadorHW[i]);

        DecimalFormat tresDigitos = new DecimalFormat("###");
        tresDigitos.setMinimumIntegerDigits(3);
        String fino = tresDigitos.format(contadorFinoHW[i]);

        DecimalFormat seteDigitos = new DecimalFormat("#,###,###");
        seteDigitos.setMinimumIntegerDigits(7);
        String deltaTotalizador = seteDigitos.format(deltaContadorTotalizador[i]);

        String deltaFino = tresDigitos.format(deltaContadorFino[i]);

        DecimalFormat dezDigitos = new DecimalFormat("#,###,###,##0.000");
        dezDigitos.setMinimumIntegerDigits(7);
        String frequenciaFinal = dezDigitos.format(frequencia[i]);


        System.out.println((ix) + "   "
                + contadorGrupo + "  "
                + datacao[i] + "    "
                + totalizador + "     "
                + fino + "     "
                + deltaTotalizador + "     "
                + deltaFino + "    "
                + frequenciaFinal);

    }

    /**
    Realiza a leitura do arquivo
    @author Antonio Cassiano
    @param nomeArquivoSelecionado
     **/
    public void lerArquivo(String nomeArquivoSelecionado) {
        FileInputStream f1;
        DataInputStream i1;
        numeroLeiturasArquivo = 0;

        try {
            //String fileArquivo = session.getServletContext().getRealPath("/") + "arquivo/arquivoCMV.txt";

            //   String fileArquivo = session.getServletContext().getRealPath("/") + "arquivo/arquivoCMVCalibracao.txt";
            String fileArquivo = session.getServletContext().getRealPath("/") + nomeArquivoSelecionado;

            f1 = new FileInputStream(fileArquivo);
            i1 = new DataInputStream(f1);
            String str = i1.readLine();

            while (str != null) {
                dadosArquivoLido[numeroLeiturasArquivo] = str;
                str = i1.readLine();
                numeroLeiturasArquivo++;
            }
        } catch (Exception e) {
            System.out.println("Erro na leitura don arquivo : " + nomeArquivoSelecionado);
        }
    }

    /**
    Configura hw com valor da frequencia de teste: 1, 10 Hz
    @author Antonio Cassiano
    @param opcao 
     **/
    public void programarHW(int opcao) {
        portaParalela.Addr = 0x378;
        portaParalela.writeData2((short) opcao);

        portaParalela.Addr = 0x37A;
        portaParalela.datum = 0x00;  // habilita CS0 para registrar o dado apenas 1 vez.
        portaParalela.writeData();
        portaParalela.datum = 0x0F;  //inibe CS0, não usar CS7
        portaParalela.writeData();



    }
}
