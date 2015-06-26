/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.ComandoDAO;
import br.faetec.model.dao.EstacaoDAO;
import br.faetec.model.dao.MissaoDAO;
import br.faetec.model.dao.SateliteDAO;
import br.faetec.model.dao.UsuarioDAO;
import br.faetec.model.dto.RequisicaoDTO;
import br.faetec.model.dto.SateliteDTO;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para Montar e enviar um email com arquivo anexo.
@author Antonio Cassiano Julio 
* fim
* 
 **/
public class MontarEmailAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /**
    Método para gravar/gerar um email com informações da requisição e com arquivo anexo.
    @author Antonio Cassiano
    @param requisicaoDTO
     **/
    public void gravarLocalHost(RequisicaoDTO requisicaoDTO) {

        String nomeUsuario = request.getParameter("nomeUsuarioLogado");

        Integer idRequisicao = requisicaoDTO.getIdRequisicao();
        int grupos = requisicaoDTO.getGrupos();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        requisicaoDTO.setUsuarioId(usuarioDAO.selecionar(requisicaoDTO.getUsuarioId()));
        String email = requisicaoDTO.getUsuarioId().getEmail();

        SateliteDAO sateliteDAO = new SateliteDAO();         // instanciou um DAO do objeto satelite
        SateliteDTO x = requisicaoDTO.getSateliteId();       //pego o id do satelite que esta no objeto requisicaoDTO proveninete do form
        SateliteDTO selecionado = sateliteDAO.selecionar(x); //busca o objeto SateliteDTO completo indicado pelo variavel x
        requisicaoDTO.setSateliteId(selecionado);           //"seta" o objeto requisicaoDTO com o objeto SateliteDTO completo passado pela variavel selecionado
        String satelite = requisicaoDTO.getSateliteId().getDescricao();
/*
        teste para subir versão
        */
        
        EstacaoDAO estacaoDAO = new EstacaoDAO();
        requisicaoDTO.setEstacaoId(estacaoDAO.selecionar(requisicaoDTO.getEstacaoId()));
        String estacao = requisicaoDTO.getEstacaoId().getDescricao();

        ComandoDAO comandoDAO = new ComandoDAO();
        requisicaoDTO.setComandoId(comandoDAO.selecionar(requisicaoDTO.getComandoId()));
        String comando = requisicaoDTO.getComandoId().getDescricao();

        MissaoDAO missaoDAO = new MissaoDAO();
        requisicaoDTO.setMissaoId(missaoDAO.selecionar(requisicaoDTO.getMissaoId()));
        String missao = requisicaoDTO.getMissaoId().getDescricao();

        System.out.println("Nome usuário: " + nomeUsuario
                + " Email : " + email
                + " Requisicao : " + idRequisicao
                + " Grupos : " + grupos
                + " satelite : " + satelite
                + " estacao : " + estacao
                + " comando : " + comando
                + " missao  : " + missao);


        SendMailLocalhost sm = new SendMailLocalhost();
        String mensagem = "Senhor Usuário " + nomeUsuario + ",\n\n"
                + ""
                + "Sua requisição de medida número " + idRequisicao + " foi efetuada com sucesso no sistema CMV3.\n\n"
                + ""
                + "Dados da Requisição:\n\n"
                + "Grupos   : " + grupos + "\n"
                + "Comando  : " + comando + "\n"
                + "Satélite : " + satelite + "\n"
                + "Estação  : " + estacao + "\n"
                + "Missão   : " + missao + "\n\n"
                + "Os dados estão disponíveis para o processamento de órbitas.\n\n"
                + ""
                + "Atenciosamente,\n"
                + "Administração CMV3\n\n"
                + "Copyright 2011 © INPE - Instituto Nacional de Pesquisas Espaciais.\n"
                + "Todos os direitos reservados.";

        String assunto = "CMV Requisição " + idRequisicao + " efetuada com sucesso.";
        sm.sendMailLocalhost("contato@cmv3.com.br", "contato", email, assunto, mensagem);
        System.out.println("\nMandei um email localhost  na Requisicao de Medida");
    }

    private void gravarGmail() {

        SendMail sm = new SendMail();
        String mensagem = "Seja bem vindo " + "Cassiano" + ",\n\n"
                + ""
                + "Teste com CMV3.\n\n"
                + ""
                + "Agradecemos a atenção\n\n"
                + ""
                + "Equipe CMV3 - 2011";
        String assunto = "Bem vindo ao Sistema CMV3";

        sm.sendMail("faetec.awr@gmail.com", "cassiano_filho@terra.com.br", assunto, mensagem);
        System.out.println("\nMandei um email via Gmail");

    }
}
