/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.RegistrologinDAO;
import br.faetec.model.dao.StatussistemaDAO;
import br.faetec.model.dao.UsuarioDAO;
import br.faetec.model.dto.RegistrologinDTO;
import br.faetec.model.dto.RespostaDTO;
import br.faetec.model.dto.StatussistemaDTO;
import br.faetec.model.dto.UsuarioDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas ao usuario, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class UsuarioAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public UsuarioAction() {
    }
    private UsuarioDTO usuarioDTO;
    private Collection<UsuarioDTO> lista;
    /*
     * Adicionado para validarLogin consultado banco
     * 07.04.2011
     */
    private String messagemErroLoginSenha;
    private String mensagemNivelAcesso;
    private String mensagemSistemaMedida;

    public String getMensagemSistemaMedida() {
        return mensagemSistemaMedida;
    }

    public void setMensagemSistemaMedida(String mensagemSistemaMedida) {
        this.mensagemSistemaMedida = mensagemSistemaMedida;
    }

    public String getMessagemErroLoginSenha() {
        return messagemErroLoginSenha;
    }

    public void setMessagemErroLoginSenha(String messagemErroLoginSenha) {
        this.messagemErroLoginSenha = messagemErroLoginSenha;
    }

    public String getMensagemNivelAcesso() {
        return mensagemNivelAcesso;
    }

    public void setMensagemNivelAcesso(String mensagemNivelAcesso) {
        this.mensagemNivelAcesso = mensagemNivelAcesso;
    }

    public Collection<UsuarioDTO> getLista() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        lista = usuarioDAO.listar();
        return lista;
    }

    public UsuarioDTO getUsuarioDTO() {
        if (usuarioDTO == null) {
            usuarioDTO = new UsuarioDTO();
        }
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    /**
     *Instancia um usuarioDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        lista = usuarioDAO.listar();
        return "listar";
    }

    /**
     *Instancia um usuarioDAO, obtem o idUsuario  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        getUsuarioDTO().setIdUsuario(new Integer(request.getParameter("idUsuario")));
        usuarioDAO.excluir(getUsuarioDTO());
        return "listar";
    }

    /**
     *Set IdUsuario com zero e permiti inserir novo registro na tabela usuario
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getUsuarioDTO().setIdUsuario(0);  // Cassiano
        return "inserir";
    }

    /**
     *Instancia um usuarioDAO, invoca o método gravar, passando  o objeto UsuarioDTO
    @author Antonio Cassiano
    @return String listar se gravar/alterar é permitido ou null caso contrário, para navegação.
     **/
    public String gravar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.gravar(getUsuarioDTO());
        //return "listar";// era até 3105
    }

    /**
     *Instancia um usuarioDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto UsuarioDTO
    @author Antonio Cassiano
    @return String alterar, para navegação.
     **/
    public String alterar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        getUsuarioDTO().setIdUsuario(new Integer(request.getParameter("idUsuario")));
        setUsuarioDTO(usuarioDAO.selecionar(getUsuarioDTO()));
        return "alterar";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String menu, para navegação.
     **/
    public String menu() {
        return "menu";
    }

    /**
     * Este método valida o login do usuário
      @author Antonio Cassiano
      @return String para o case do rule navigator no arquivo faces-config.xml
     */
    public String validarLogin() {
        String retorno = null;
        setMessagemErroLoginSenha(null);//  mensagem vazia

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDTO = usuarioDAO.validarLogin(getUsuarioDTO());

            System.out.println("\nUsuario : " + usuarioDTO.getNome() + "  Cargo : " + usuarioDTO.getCargo().getDescricao() + "  idCargo : " + usuarioDTO.getCargo().getIdCargo());
            /* Instancia DAO e DTO
             * coloco o usuario logado todo preenchido no registro login
             * persistencia no BD
             * @since 31/03/2011
             */
            RegistrologinDAO registrologinDAO = new RegistrologinDAO();
            RegistrologinDTO registrologin = new RegistrologinDTO();

            registrologin.setUsuarioId(usuarioDTO);
            registrologinDAO.gravar(registrologin);

            nivelAcesso(); // verifique a navegação no arquivo faces-config.xml
            retorno = "menu";

        } catch (NoResultException erro) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Usuário não cadastrado ou senha inválida!",
                    "Usuário não cadastrado ou senha inválida!"));

            retorno = null;
        }
        return retorno;
    }

    /**
     * Este método verifica o nivel de acesso e monta mensagem adequada
      @author Antonio Cassiano
     */
    public void nivelAcesso() {
        Integer idCargo = usuarioDTO.getCargo().getIdCargo();
        System.out.println("\n Teste NIVEL DE ACESSO:  " + idCargo + "   ##########\n");
        if (idCargo == 1) {
            setMensagemNivelAcesso("Acesso Completo");
            System.out.println("\n ACESSO COMPLETO.");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Acesso Completo!",
                    "Acesso Completo!"));
        } else {
            setMensagemNivelAcesso("Acesso para Operação");
            System.out.println("\n ACESSO OPERAÇAO.");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Acesso para Operação!",
                    "Acesso para Operação!"));
        }
    }

     /**
     * Este método obtem  o status do sistema
      @author Antonio Cassiano
      @return respostaDTO
     */
    public RespostaDTO obtemStatusSistema() {
        RespostaDTO respostaDTO = new RespostaDTO();
        StatussistemaDAO xDAO = new StatussistemaDAO();
        respostaDTO.getStatussistemaId().setIdStatussistema(4);       //pego o id do statussistema que esta no objeto respostaoDTO
        StatussistemaDTO x = respostaDTO.getStatussistemaId();
        StatussistemaDTO selecionado = xDAO.selecionar(x);
        respostaDTO.setStatussistemaId(selecionado);
        return respostaDTO;
    }

    /**
     * Valida o valor dos campos
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void checkLogin(FacesContext context, UIComponent component, Object value) {
        String campo = component.getClientId();
        String valor = (String) value;
        if (value == null) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("O campo LOGIN é obrigatório.");
            message.setDetail("O campo LOGIN é obrigatório.");
            context.addMessage("loginID", message);
            throw new ValidatorException(message);
        }
    }
}
