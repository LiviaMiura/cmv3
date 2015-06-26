/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.RespostaDAO;
import br.faetec.model.dto.RespostaDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas a resposta, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class RespostaAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public RespostaAction() {
    }
    private RespostaDTO respostaDTO;
    private Collection<RespostaDTO> lista;


    public Collection<RespostaDTO> getLista() {
        RespostaDAO respostaDAO = new RespostaDAO();
        lista = respostaDAO.listar();
        return lista;
    }

    public RespostaDTO getRespostaDTO() {
        if (respostaDTO == null) {
            respostaDTO = new RespostaDTO();
        }
        return respostaDTO;
    }

    public void setRespostaDTO(RespostaDTO respostaDTO) {
        this.respostaDTO = respostaDTO;
    }




      /**
     *Instancia uma respostaDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        RespostaDAO respostaDAO = new RespostaDAO();
        lista = respostaDAO.listar();
        return "listar";
    }

     /**
     *Instancia uma respostaDAO, obtem o idResposta  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        RespostaDAO respostaDAO = new RespostaDAO();
        getRespostaDTO().setIdResposta(new Integer(request.getParameter("idResposta")));
        respostaDAO.excluir(getRespostaDTO());
        return "listar";
    }



     /**
     *Set IdResposta com zero e permiti inserir novo registro na tabela resposta
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getRespostaDTO().setIdResposta(0);  // Cassiano
        return "inserir";
    }


     /**
     *Instancia uma respostaDAO, invoca o método gravar, passando  o objeto RespostaDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        RespostaDAO respostaDAO = new RespostaDAO();
        respostaDAO.gravar(getRespostaDTO());
        return "listar";
    }

     /**
     *Instancia uma respostaDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto RespostaDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        RespostaDAO respostaDAO = new RespostaDAO();
        getRespostaDTO().setIdResposta(new Integer(request.getParameter("idResposta")));
        setRespostaDTO(respostaDAO.selecionar(getRespostaDTO()));
        return "inserir";
    }

     /**
    *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String menu() {
        return "menu";
    }

}