/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.SateliteDAO;
import br.faetec.model.dto.SateliteDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas ao satelite, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class SateliteAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public SateliteAction() {
    }
    private SateliteDTO sateliteDTO;
    private Collection<SateliteDTO> lista;


    public Collection<SateliteDTO> getLista() {
        SateliteDAO sateliteDAO = new SateliteDAO();
        lista = sateliteDAO.listar();
        return lista;
    }

    public SateliteDTO getSateliteDTO() {
        if (sateliteDTO == null) {
            sateliteDTO = new SateliteDTO();
        }
        return sateliteDTO;
    }

    public void setSateliteDTO(SateliteDTO sateliteDTO) {
        this.sateliteDTO = sateliteDTO;
    }


       /**
     *Instancia um sateliteDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        SateliteDAO sateliteDAO = new SateliteDAO();
        lista = sateliteDAO.listar();
        return "listar";
    }

     /**
     *Instancia um sateliteDAO, obtem o idCargo  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        SateliteDAO sateliteDAO = new SateliteDAO();
        getSateliteDTO().setIdSatelite(new Integer(request.getParameter("idSatelite")));
        sateliteDAO.excluir(getSateliteDTO());
        return "listar";
    }


      /**
     *Set IdSatelite com zero e permiti inserir novo registro na tabela cargo
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getSateliteDTO().setIdSatelite(0);  // Cassiano
        return "inserir";
    }



    /**
     *Instancia um sateliteDAO, invoca o método gravar, passando  o objeto SateliteDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        SateliteDAO sateliteDAO = new SateliteDAO();
        sateliteDAO.gravar(getSateliteDTO());
        return "listar";
    }

      /**
     *Instancia um sateliteDAO, obtem o idSatelite e invoca o método selecionar, passando  o objeto SateliteDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        SateliteDAO sateliteDAO = new SateliteDAO();
        getSateliteDTO().setIdSatelite(new Integer(request.getParameter("idSatelite")));
        setSateliteDTO(sateliteDAO.selecionar(getSateliteDTO()));
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