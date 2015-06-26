/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.hardware.util.AquisicaoAction;
import br.faetec.model.dao.MedidaDAO;
import br.faetec.model.dao.RequisicaoDAO;
import br.faetec.model.dao.RespostaDAO;
import br.faetec.model.dao.SateliteDAO;
import br.faetec.model.dto.MedidaDTO;
import br.faetec.model.dto.RequisicaoDTO;
import br.faetec.model.dto.RespostaDTO;
import br.faetec.model.dto.SateliteDTO;

import java.io.DataInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos referente a requisicao de medidas, get/set, grava requisicao,
grava respostas, grava resultados e grava medidas e strings para navegação.
@author Antonio Cassiano
 **/
public class RequisicaoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    private String messagemFimMedidas;
    private String messagemInicioMedidas;
    private String mensagemComandoInvalido;
    private String mensagemTerminoPrematuro;
    int i = 0;
    int janelaMedida = 1;
    int numeroGrupo = 1;
    int numeroLeiturasArquivoProcessado;
    int tamanho = 7200;  // duas horas de teste
    public static int termino = 0;
    static int total = 0;
    int numeroGrupoSelecionado = 1;
    int idComando = 1;
    int testeJanelaMedida = 1;
    String nomeArquivo;
    String dadosArquivoLidoProcessado[] = new String[tamanho];
    String iLido[] = new String[tamanho];
    String contadorGrupoLido[] = new String[tamanho];
    String datacaoLido[] = new String[tamanho];
    long contadorTotalizadorLido[] = new long[tamanho];
    long contadorFinoLido[] = new long[tamanho];
    long deltaContadorTotalizador[] = new long[tamanho];
    long deltaContadorFino[] = new long[tamanho];
    long ajusteFino[] = new long[tamanho];
    double frequencia[] = new double[tamanho];
    private String mensagemStatusSistema;

    public String getMensagemStatusSistema() {
        return mensagemStatusSistema;
    }

    public void setMensagemStatusSistema(String mensagemStatusSistema) {
        this.mensagemStatusSistema = mensagemStatusSistema;
    }

    public static int getTermino() {
        return termino;
    }

    public static void setTermino(int termino) {
        RequisicaoAction.termino = termino;
    }

    public String getMensagemTerminoPrematuro() {
        return mensagemTerminoPrematuro;
    }

    public void setMensagemTerminoPrematuro(String mensagemTerminoPrematuro) {
        this.mensagemTerminoPrematuro = mensagemTerminoPrematuro;
    }

    public String getMessagemFimMedidas() {
        return messagemFimMedidas;
    }

    public void setMessagemFimMedidas(String messagemFimMedidas) {
        this.messagemFimMedidas = messagemFimMedidas;
    }

    public String getMessagemInicioMedidas() {
        return messagemInicioMedidas;
    }

    public void setMessagemInicioMedidas(String messagemInicioMedidas) {
        this.messagemInicioMedidas = messagemInicioMedidas;
    }

    public String getMensagemComandoInvalido() {
        return mensagemComandoInvalido;
    }

    public void setMensagemComandoInvalido(String mensagemComandoInvalido) {
        this.mensagemComandoInvalido = mensagemComandoInvalido;
    }

    /** Creates a new instance of RequisicaoAction */
    public RequisicaoAction() {
    }
    private RequisicaoDTO requisicaoDTO;
    private Collection<RequisicaoDTO> lista;

    public Collection<RequisicaoDTO> getLista() {
        RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
        lista = requisicaoDAO.listar();
        return lista;
    }

    public void setLista(Collection<RequisicaoDTO> lista) {
        this.lista = lista;
    }

    public RequisicaoDTO getRequisicaoDTO() {
        if (requisicaoDTO == null) {
            requisicaoDTO = new RequisicaoDTO();
        }
        return requisicaoDTO;
    }

    public void setRequisicaoDTO(RequisicaoDTO requisicaoDTO) {
        this.requisicaoDTO = requisicaoDTO;
    }

    /**
     *Instancia uma requisicaoDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
        lista = requisicaoDAO.listar();
        return "listar";
    }

    /**
     *Instancia uma requisicaoDAO, obtem o idRequisicao  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
        getRequisicaoDTO().setIdRequisicao(new Integer(request.getParameter("idRequisicao")));
        requisicaoDAO.excluir(getRequisicaoDTO());
        return "listar";
    }

    /**
    Configura valor inicial para grupos igual 1 e comando 1 (medida de 1s), na chamada da requisicao
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getRequisicaoDTO().setGrupos(1);
        getRequisicaoDTO().getComandoId().setIdComando(1);
        return "inserir";
    }

    /**
     *Instancia uma requisicaoDAO, obtem o idRrequisicao e invoca o método selecionar, passando  o objeto requisicoDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
        getRequisicaoDTO().setIdRequisicao(new Integer(request.getParameter("idRequisicao")));
        setRequisicaoDTO(requisicaoDAO.selecionar(getRequisicaoDTO()));
        return "alterar";
    }

    /**
    Este método verifica a presenca do hardware, se verdadeiro permite o acesso ao hardware, caso contrario apenas emulaçao,
    Verifica também o status do sistema se disponivel ou medida em andamento e gera mensagem para o usuario.
    @author Antonio Cassiano
    @return String requisicao para "nova requisicao" ou null caso contrario,conforme faces-config para a navegação.
     **/
    public String requisicao() {
        String retorno = "requisicao";
        if (aquisicaoStatusHardware()) {
            getRequisicaoDTO().setTipoAquisicao(1);
            getRequisicaoDTO().setStatusHWAtual(true);
            retorno = "requisicao";
            setMensagemStatusSistema("Hardware OK!");
            System.out.println("\n VALOR INICIAL GRUPO E COMANDO CONFIGURADO NA REQUISICAO ###############!");

            if (pegaStatusSistema()) {
                setMensagemStatusSistema("Sistema com Requisição em andamento!");
                System.out.println("Sistema com Requisição em andamento!");
                retorno = null;
            } else {
                setMensagemStatusSistema("Sistema Disponível");
                System.out.println("Sistema Disponível!");
            }

        } else {
            getRequisicaoDTO().setTipoAquisicao(0);
            getRequisicaoDTO().setStatusHWAtual(false);
            retorno = "emulacao";
            setMensagemStatusSistema("Hardware Ausente, Emulação Ativada.");
            System.out.println("Hardware Ausente, Emulação Ativada.");
        }
        getRequisicaoDTO().setGrupos(1);
        getRequisicaoDTO().getComandoId().setIdComando(1);

        return retorno;
    }

    /**
    Este método verifica o status do sistema
    @author Antonio Cassiano
    @return boolean resposta - true se disponivel, caso contrario false.
     **/
    public boolean pegaStatusSistema() {
        boolean resposta = false;
        getRequisicaoDTO().setStatusSistemaMedida(false);

        AquisicaoAction teste = new AquisicaoAction();
        long leituraStatusSistema = teste.leituraStatusSistema();
        if (leituraStatusSistema == 1) {  //sistema em medida
            resposta = true;
            getRequisicaoDTO().setStatusSistemaMedida(true);
        }
        return resposta;
    }

    /**
    Este método inicia o processo para efetuar uma requisicao, válida configuração
    invoca o acesso ao hardware ou arquivo, grava requisicao, resposta e medidas e solicita o envio de email.
    @author Antonio Cassiano
    @throws InterruptedException
     **/
    public String gravar() throws InterruptedException {

        String retorno = "andamento";
        total = capturaConfiguracaoAquisicao();

        System.out.println("\nTotal : " + total);
        retorno = aquisicaoAcessoHardware();


        //efetua o processamento principal com acesso ao hw ou arquivo
        System.out.print("\nFim Aquisicao, PROCESSAMENTO PRINCIPAL.");
        RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
        requisicaoDAO.gravar(getRequisicaoDTO());

        if (!validarComando(requisicaoDTO)) {
            gravarRespostaMensagemNaoIdentificada();
        } else {
            if (!validarEstacao(requisicaoDTO)) {
                gravarRespostaPrimariaRejeitada();
            } else {
                gravarRespostaPrimariaAceita();
                if (getTermino() == 3) {
                    nomeArquivo = "arquivo/HardwareCMVZERO.txt";
                }
                aquisicaoDados(nomeArquivo);
                gravarRespostaTransacaoMedida();
            }  // fim gravação
        }   // fim RespostaMensagemNãoIdentificada
        // fim da aquisicao HW ou emulação
        ExcelBean teste = new ExcelBean();
        teste.criarPlanilha();
        MontarEmailAction emailLocal = new MontarEmailAction();
        emailLocal.gravarLocalHost(requisicaoDTO);
        //  }
        return retorno;
    }

    /**
    Este método obtem configuração do numero grupos e tamanho da janela de medida para janela de 1s e
    válida valor limite para janela de 10s.
    @author Antonio Cassiano
    @return  (numeroGrupoSelecionado * testeJanelaMedida)
     **/
    public int capturaConfiguracaoAquisicao() {
        /* Usando getParameter, recupero  da pagina "form" a variavel  idUsuarioLogado
         * e carrego no getRequisicaoDTO com os demais parametros obtidos do form
         * para uma requisicao aceita indo para fase de andamento  05/04/2011
         */

        getRequisicaoDTO().getUsuarioId().setIdUsuario(new Integer(request.getParameter("idUsuarioLogado")));

        numeroGrupoSelecionado = requisicaoDTO.getGrupos();
        idComando = requisicaoDTO.getComandoId().getIdComando();
        testeJanelaMedida = 1;

        if (idComando == 2) {
            testeJanelaMedida = 10;
            if (numeroGrupoSelecionado > 2) {
                numeroGrupoSelecionado = 2;
                getRequisicaoDTO().setGrupos(numeroGrupoSelecionado);
                System.out.println("\n ----------------- >> Teste AUTO AJUSTE GRUPOS = 2!");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Auto Ajuste, Grupo = 2!", "Auto Ajuste, Grupo = 2!"));
            }
        }
        return (numeroGrupoSelecionado * testeJanelaMedida);
    }

    /**
    Este método efetua a aquisicao acessando o hardware, abrindo e fechando
    a janela de aquisicao  sincrono com o hardware e controle do arquivo CalibracaoCMV.txt
    @author Antonio Cassiano
    @return String andamento  conforme faces-config para a navegação.
     **/
    public String aquisicaoAcessoHardware() throws InterruptedException {
        String retorno = "andamento";
        nomeArquivo = "arquivo/CalibracaoCMV.txt";

        if (getRequisicaoDTO().getTipoAquisicao() == 1) {  //hw = 1


            if (aquisicaoStatusHardware()) {
                nomeArquivo = "arquivo/HardwareCMV.txt";
                setTermino(0);
                enviarStartSW(); // ABRE JANELA DE MEDIDA
                Thread.sleep(1000);  //ajuste para eliminar leituras inicial

                aquisicaoHardware(numeroGrupoSelecionado, testeJanelaMedida, nomeArquivo);

                long t = ((10 * numeroGrupoSelecionado * testeJanelaMedida * 1000) + 2000);
                Thread.sleep(t);
                enviarStopSW();  // FECHA JANELA MEDIDA
                System.out.print("\n O tempo passa ..........Opa fim Aquisicao Hardware t: " + t);
            }
        }
        return retorno;
    }

    /**
    Este método efetua a leitura do arquivo de dados para janela de 1 ou 10s
    @author Antonio Cassiano
    @param nomeArquivoSelecionado
     **/
    public void aquisicaoDados(String nomeArquivoSelecionado) {
        System.out.print("\nLendo  arquivo ok !\n");
        AquisicaoAction aquisicaoArquivo = new AquisicaoAction();
        aquisicaoArquivo.lerArquivo(nomeArquivoSelecionado);
        idComando = getRequisicaoDTO().getComandoId().getIdComando();

        if (idComando == 1) {
            janelaMedida = 1;

        } else {
            janelaMedida = 10;
        }

        System.out.println("Janela de Medida :  " + janelaMedida);

        numeroGrupo = numeroGrupoSelecionado;

        // numeroGrupo = getRequisicaoDTO().getGrupos();
        try {
            //processa medidas e monta Arquivo Processado, AQUI CASSIANO !!!!!
            aquisicaoArquivo.preparaProcessaArquivo(numeroGrupo, janelaMedida);
        } catch (IOException ex) {
            Logger.getLogger(RequisicaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
    Este método grava a resposta de transacao de medida a partir do arquivo processado e configuracao do requisicao
     * @author Antonio Cassiano
    @throws InterruptedException
     **/
    public void gravarRespostaTransacaoMedida() throws InterruptedException {
        int numeroLeituras = lerArquivoProcessado();
        pegaArquivoProcessado(0, numeroLeituras);

        // int grupos = getRequisicaoDTO().getGrupos();
        int grupos = numeroGrupo;
        RespostaDTO respostaDTO;

        if (janelaMedida == 1) {
            i = 2;   // inicio das medidas válidas para janela de 1s
        } else {
            i = 1;    // inicio das medidas válidas para janela de 10s
        }

        do {
            int mensagem = grupos - 1;
            respostaDTO = gravarRespostaTransacao(mensagem);
            for (int j = 0; j < 10; j++) {

                // oi aqui
                gravarMedidas(respostaDTO,
                        mensagem,
                        datacaoLido[i],
                        contadorTotalizadorLido[i],
                        deltaContadorFino[i],
                        frequencia[i]);
                i++;
            }
            grupos--;
        } while (grupos != 0);
    }

    /**
    Este método grava a resposta as medidas efetuadas
    @author Antonio Cassiano
    @param respostaDTO
    @param mensagem
    @param datacaoLido
    @param contadorTotalizadorLido
    @param deltaContadorFino
    @param frequencia
     **/
    private void gravarMedidas(RespostaDTO respostaDTO, int mensagem, String datacaoLido, long contadorTotalizadorLido, long deltaContadorFino, double frequencia) {
        Integer idResposta = respostaDTO.getIdResposta();

        System.out.println("" + idResposta + "   " + contadorTotalizadorLido + "       " + deltaContadorFino
                + "    " + frequencia);

        MedidaDAO medidaDAO = new MedidaDAO();
        MedidaDTO medidaDTO = new MedidaDTO();

        medidaDTO.getRespostaId().setIdResposta(idResposta);

        medidaDTO.setMensagem(mensagem);
//modificação 21052011
        medidaDTO.setUtc(datacaoLido);

        medidaDTO.setTotalizador((int) contadorTotalizadorLido);
        medidaDTO.setIntervalo((int) deltaContadorFino);
        medidaDTO.setFrequencia(frequencia);
        medidaDAO.gravar(medidaDTO);
    }

    /**
    Este método efetua a leitura do arquivo processado
    @author Antonio Cassiano
    @return numeroLeiturasArquivoProcessado
     **/
    public int lerArquivoProcessado() {
        FileInputStream f1;
        DataInputStream i1;
        numeroLeiturasArquivoProcessado = 0;

        try {

            String fileArquivo = request.getRealPath("/") + "arquivo/arquivoCMVProcessado.txt";

            f1 = new FileInputStream(fileArquivo);
            i1 = new DataInputStream(f1);
            String str = i1.readLine();

            while (str != null) {
                dadosArquivoLidoProcessado[numeroLeiturasArquivoProcessado] = str;
                str = i1.readLine();
                numeroLeiturasArquivoProcessado++;
            }
        } catch (Exception e) {
            System.out.println("Erro na leitura do arquivo ArquivoCMVProcessado.txt!!! ");
        }
        return numeroLeiturasArquivoProcessado;
    }

    /**
    Este método recupera o arquivo processado - leitura
    @author Antonio Cassiano
    @param i - indice do vetor
    @param numeroLeituras 
     **/
    public void pegaArquivoProcessado(int i, int numeroLeituras) {
        do {
            String dados = dadosArquivoLidoProcessado[i];       //separacao, por espaço em branco, da string dadosArquivoLido
            //System.out.println("" + dados);
            String[] dado = dados.split("\\ ");
            iLido[i] = dado[0];
            contadorGrupoLido[i] = dado[1];
            datacaoLido[i] = dado[2];
            contadorTotalizadorLido[i] = Long.parseLong(dado[3]);
            contadorFinoLido[i] = Long.parseLong(dado[4]);
            deltaContadorTotalizador[i] = Long.parseLong(dado[5]);
            deltaContadorFino[i] = Long.parseLong(dado[6]);
            frequencia[i] = Double.parseDouble(dado[7]);
            i++;
        } while (i < numeroLeituras);
    }

    /**
    Este método grava a resposta primaria aceita
    @author Antonio Cassiano
     **/
    public void gravarRespostaPrimariaAceita() {
        RespostaDAO respostaDAO = new RespostaDAO();
        RespostaDTO respostaDTO = new RespostaDTO();
        Integer idRequisicao = getRequisicaoDTO().getIdRequisicao();

        //ou respostaDTO.setRequisicaoId(requisicaoDTO);             //requisicao atual
        respostaDTO.getRequisicaoId().setIdRequisicao(idRequisicao);
        respostaDTO.getResultadoId().setIdResultado(1);             //requisicao aceita
        respostaDTO.getStatussistemaId().setIdStatussistema(1);
        respostaDTO.getStatushwId().setIdStatushw(1);
        respostaDTO.setMensagem(0);                         // mensagem = 0 para resposta de transacao
        respostaDTO.setAmostras(10);                             //amostras  =10 , para resposta transacao fixo em 10
        respostaDAO.gravar(respostaDTO);
        // System.out.println("\n GRAVEI RESPOSTA PRIMARIA PARA REQUISICAO ACEITA :  " + getRequisicaoDTO().getIdRequisicao() + " NO BD  !");
    }

    /**
    Este método grava a resposta transacao de acordo com o numero de grupos
    @author Antonio Cassiano
    @param grupos - numero de grupos
     **/
    public RespostaDTO gravarRespostaTransacao(int grupos) {
        RespostaDAO respostaDAO = new RespostaDAO();
        RespostaDTO respostaDTO = new RespostaDTO();

        Integer idRequisicao = getRequisicaoDTO().getIdRequisicao();

        respostaDTO.getRequisicaoId().setIdRequisicao(idRequisicao);
        respostaDTO.getResultadoId().setIdResultado(3);           //resposta de transacao
        respostaDTO.getStatussistemaId().setIdStatussistema(1);
        respostaDTO.getStatushwId().setIdStatushw(1);
        respostaDTO.setMensagem(grupos);                         // mensagem = grupos, para resposta de transacao
        respostaDTO.setAmostras(10);                             //amostras  =10 , para resposta transacao fixo em 10

        respostaDAO.gravar(respostaDTO);

        System.out.println("\n GRAVEI RESPOSTA TRANSACAO PARA REQUISICAO : " + getRequisicaoDTO().getIdRequisicao() + "  MENSAGEM : " + grupos + " NO BD  !");
        System.out.println("\nResp.  Totalizador   Int.  Frequencia ");

        RespostaDTO xDTO = respostaDTO;
        return xDTO;
    }

    /**
    Este método grava a resposta de mesnagem nao identificada
    @author Antonio Cassiano
     **/
    public void gravarRespostaMensagemNaoIdentificada() {
        RespostaDAO respostaDAO = new RespostaDAO();
        RespostaDTO respostaDTO = new RespostaDTO();
        Integer idRequisicao = getRequisicaoDTO().getIdRequisicao();

        respostaDTO.getRequisicaoId().setIdRequisicao(idRequisicao);
        respostaDTO.getResultadoId().setIdResultado(6);             //comando invalido, mensagem nao identificada
        respostaDTO.getStatussistemaId().setIdStatussistema(1);
        respostaDTO.getStatushwId().setIdStatushw(1);
        respostaDTO.setMensagem(0);                                //mensagem =  0 para comando invalido
        respostaDTO.setAmostras(0);                                //amostras  = 0 para resposta de mensagem nao identificada
        respostaDAO.gravar(respostaDTO);
        System.out.println("\n GRAVEI RESPOSTA MENSAGEM NÃO IDENTIFICADA PARA REQUISICAO :  " + getRequisicaoDTO().getIdRequisicao() + " NO BD  !");
    }

    /**
    Este método grava a resposta primaria rejeitada
    @author Antonio Cassiano
     **/
    public void gravarRespostaPrimariaRejeitada() {
        RespostaDAO respostaDAO = new RespostaDAO();
        RespostaDTO respostaDTO = new RespostaDTO();
        Integer idRequisicao = getRequisicaoDTO().getIdRequisicao();

        respostaDTO.getRequisicaoId().setIdRequisicao(idRequisicao);
        respostaDTO.getResultadoId().setIdResultado(2);             //estacao errada, resposta primaria requisicao rejeitada
        respostaDTO.getStatussistemaId().setIdStatussistema(1);
        respostaDTO.getStatushwId().setIdStatushw(1);
        respostaDTO.setMensagem(0);                                //mensagem =  0 para resposta primaria requisicao rejeitada
        respostaDTO.setAmostras(10);                               //amostras  = 10 para resposta primaria requisicao rejeitada

        respostaDAO.gravar(respostaDTO);
        System.out.println("\n GRAVEI RESPOSTA MENSAGEM REJEITADA PARA REQUISICAO :  " + getRequisicaoDTO().getIdRequisicao() + " NO BD  !");
    }

    /**
    Este método valida o comando solicitado
    @author Antonio Cassiano
    @return true se comando aceito, caso contrario false
     **/
    private boolean validarComando(RequisicaoDTO requisicaoDTO) {
        boolean retorno = true;
        idComando = requisicaoDTO.getComandoId().getIdComando();
        if (idComando == 4) {
            retorno = false;
        }
        return retorno;
    }

    /**
    Este método valida o estacao solicitada
    @author Antonio Cassiano
    @return true se estacao aceita, caso contrario false
     **/
    private boolean validarEstacao(RequisicaoDTO requisicaoDTO) {
        boolean retorno = true;
        Integer idEstacao = requisicaoDTO.getEstacaoId().getIdEstacao();
        if (idEstacao == 3) {
            retorno = false;
        }
        return retorno;
    }

    /**
    Este método verifica a presenca do hardware, se verdadeiro permite o acesso ao hardware para calibracao
    , caso contrario nao permite o acesso
    Verifica também o status do sistema se disponivel ou medida em andamento e gera mensagem para o usuario.
    @author Antonio Cassiano
    @return String calibracao para "nova calibracao" ou null caso contrario,conforme faces-config para a navegação.
     **/
    public String calibracao() {
        String retorno = "calibracao";
        if (aquisicaoStatusHardware()) {
            getRequisicaoDTO().setGrupos(1);
            getRequisicaoDTO().getComandoId().setIdComando(1);
            getRequisicaoDTO().setTaxaAquisicao(0);
            getRequisicaoDTO().setStatusHWAtual(true);

            retorno = "calibracao";
            setMensagemStatusSistema("Status: Hardware OK!");
            System.out.println("\n VALOR INICIAL GRUPO E COMANDO CONFIGURADO NA CALIBRACAO ###############!");

            if (pegaStatusSistema()) {
                setMensagemStatusSistema("Status: Sistema com Requisição de Medida em andamento!");
                System.out.println("Status: Sistema com Requisição de Medida em andamento!");
                retorno = null;
            } else {
                setMensagemStatusSistema("Status: Sistema Disponivel");
                System.out.println("Status: Sistema Disponivel!");
            }

        } else {

            getRequisicaoDTO().setStatusHWAtual(false);
            retorno = null;
            setMensagemStatusSistema("Status: Hardware Ausente.");
            System.out.println("Status: Hardware Ausente.");
        }

        return retorno;
    }

    /**
    Este método configura e efeta a calibracao do hardware e gera o arquivo CalibracaoCMV.txt
    Verifica também o status do sistema se disponivel ou medida em andamento e gera mensagem para o usuario.
    @author Antonio Cassiano
    @return String  null ,conforme faces-config para a navegação.
     **/
    public String calibracaoHardware() throws InterruptedException {

        getRequisicaoDTO().getUsuarioId().setIdUsuario(new Integer(request.getParameter("idUsuarioLogado")));


        numeroGrupoSelecionado = requisicaoDTO.getGrupos();
        int taxaAquisicao = requisicaoDTO.getTaxaAquisicao();
        testeJanelaMedida = 1;
        nomeArquivo = "arquivo/CalibracaoCMV.txt";

        total = numeroGrupoSelecionado;


        enviarStartSW();
        Thread.sleep(1000);  //ajuste para eliminar leituras inicial
        System.out.println("\nNúmero de grupos : " + numeroGrupoSelecionado + "   Acesso ao HW usuário: " + request.getParameter("idUsuarioLogado"));


        AquisicaoAction acessoHardware = new AquisicaoAction();
        acessoHardware.programarHW(taxaAquisicao);
        acessoHardware.lerHardware(numeroGrupoSelecionado, testeJanelaMedida, nomeArquivo);

        long t = ((10 * numeroGrupoSelecionado * testeJanelaMedida * 1000) + 3000);
        Thread.sleep(t);
        System.out.print("\n O tempo passa ..........Opa fim Aquisicao Hardware t: " + t);
        AquisicaoAction comandoStopSW = new AquisicaoAction();
        comandoStopSW.stopSW();

        setMessagemInicioMedidas("Medidas em andamento"); // mensagem para pagina

        AquisicaoAction aquisicao = new AquisicaoAction();
        aquisicao.programarHW(0);// padrao inicial  1 pps  16/05
        return null;
    }

    /**
    Este método  efetua a aquisicao do status do hardware
    @author Antonio Cassiano
    @return retorno - true se hardware presente, false de ausente.
     **/
    public boolean aquisicaoStatusHardware() {
        boolean retorno = false;
        AquisicaoAction acessoHardware = new AquisicaoAction();
        if (acessoHardware.leituraStatusHw() == 165) {
            retorno = true;
        }
        return retorno;
    }

    /**
    Este método  efetua a aquisicao do hardware conforme configuração da requisicao
    @author Antonio Cassiano
    @param numeroGrupoSelecionado
    @param testeJanelaMedida
    @param arquivoHardware
     **/
    public void aquisicaoHardware(int numeroGrupoSelecionado, int testeJanelaMedida, String arquivoHardware) throws InterruptedException {

        System.out.println("\nNúmero de grupos :  " + numeroGrupoSelecionado + "  Janela :  " + testeJanelaMedida + "  Acesso ao HW usuário:  " + request.getParameter("idUsuarioLogado"));
        System.out.print("\nOpa inicio Aquisicao Hardware");
        AquisicaoAction acessoHardware = new AquisicaoAction();
        acessoHardware.lerHardware(numeroGrupoSelecionado, testeJanelaMedida, arquivoHardware);


    }

    /**
    Este método  gera a mensagem de fim de medidas
    @author Antonio Cassiano
     **/
    public String pararMedidaHardware() {
        setMessagemFimMedidas("Fim das Medidas!");
        System.out.println("FIM das medidas Hardware &&&&&&&&&&&&&&&&&&&\n");
        return null;
    }

    /**
    Este método  grava o termino da medida
    @author Antonio Cassiano
     *@return andamento - para navegacao
     **/
    public String gravarTermino() {
        /*
         *Usando getParameter, recupero  da pagina "form" a variavel  idUsuarioLogado
         *e carrego no getRequisicaoDTO para uma requisicao em fase de andamento com termino prematuro
         */
        String retorno = "andamento";

        if (getRequisicaoDTO().getGrupos() < 4) {
            setTermino(3);   // flag de termino prematuro
            getRequisicaoDTO().getUsuarioId().setIdUsuario(new Integer(request.getParameter("idUsuarioLogado")));
            getRequisicaoDTO().getComandoId().setIdComando(3);
            RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
            requisicaoDAO.gravar(getRequisicaoDTO());  // grava requisicao
            System.out.println("\n GRAVEI TERMINO PREMATURO NA REQUISICAO   !!!!!");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Término Prematuro aceito!", "Término Prematuro aceito!"));

            if (!validarEstacao(requisicaoDTO)) {
                gravarRespostaPrimariaRejeitada();
            } else {
                gravarRespostaPrimariaAceita();
                gravarRespostaTerminoPrematuro();
            }
        } else {
            System.out.println("\n TERMINO PREMATURO NAO ACEITO, GRUPOS > 3 !!!!!");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Término Prematuro não aceito!", "Término Prematuro não aceito!"));

        }


        return retorno;
    }

    /**
    Este método  envia o comando start software e libera contagem
    @author Antonio Cassiano
    @return null - para navegacao
     **/
    public String enviarStartSW() {
        String retorno = null;
        AquisicaoAction aquisicao = new AquisicaoAction();
        aquisicao.startSW();

        return retorno;
    }

    /**
    Este método  envia o comando stop software
    @author Antonio Cassiano
    @return retorno - null para navegacao
     **/
    public String enviarStopSW() {
        String retorno = null;
        AquisicaoAction aquisicao = new AquisicaoAction();
        aquisicao.stopSW();

        aquisicao.programarHW(0);// padrao inicial  1 pps  16/05
        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Reset MMF enviado!", "Reset MMF enviado!"));

        return retorno;
    }

    /**
    Este método  grava o termino prematuto da medida
    @author Antonio Cassiano  
     **/
    public void gravarRespostaTerminoPrematuro() {
        RespostaDAO respostaDAO = new RespostaDAO();
        RespostaDTO respostaDTO = new RespostaDTO();

        Integer idRequisicao = getRequisicaoDTO().getIdRequisicao();
        respostaDTO.getRequisicaoId().setIdRequisicao(idRequisicao);
        respostaDTO.getResultadoId().setIdResultado(5);      //idresultado =5, para resposta de termino prematuro
        respostaDTO.getStatussistemaId().setIdStatussistema(1);
        respostaDTO.getStatushwId().setIdStatushw(1);
        respostaDTO.setMensagem(0);                         // mensagem = 0, para resposta de  termino prematuro
        respostaDTO.setAmostras(0);                         //amostras  =0, para resposta de termino prematuro
        respostaDAO.gravar(respostaDTO);

    }

    /**
    Este método  controla a navegação
    @author Antonio Cassiano
    @return string menu para navegação
     **/
    public String menu() {
        return "menu";
    }

    /**
    Este método  controla a navegação
    @author Antonio Cassiano
    @return string historicoRequisicoes para navegação
     **/
    public String historicoRequisicoes() {
        return "historicoRequisicoes";
    }

    /**
    Este método obtem dados para configuração do hardware provenientes do form
    @author Antonio Cassiano
     **/
    public void obtemDadosparaHardware() {

        System.out.println("\n Teste Hardware USUARIO:  " + requisicaoDTO.getUsuarioId().getIdUsuario() + "   #########\n");
        System.out.println("Número de grupos : " + requisicaoDTO.getGrupos() + "    **********\n");

        SateliteDAO sateliteDAO = new SateliteDAO();         // instanciou um DAO do objeto satelite
        SateliteDTO x = requisicaoDTO.getSateliteId();       //pego o id do satelite que esta no objeto requisicaoDTO proveninete do form

        SateliteDTO selecionado = sateliteDAO.selecionar(x); //busca o objeto SateliteDTO completo indicado pelo variavel x
        requisicaoDTO.setSateliteId(selecionado);           //"seta" o objeto requisicaoDTO com o objeto SateliteDTO completo passado pela variavel selecionado
        //requisicaoDTO.setSateliteId(sateliteDAO.selecionar(requisicaoDTO.getSateliteId()));  // versao Joel   11/04/2011
        System.out.println("Satelite : " + requisicaoDTO.getSateliteId().getDescricao() + "   &&&&&&&&&&\n");
    }
}
