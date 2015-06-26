/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.RegistrologinDAO;
import br.faetec.model.dto.RegistrologinDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas ao registrologin, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class RegistrologinAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public RegistrologinAction() {
    }
    private RegistrologinDTO cargoDTO;
    private Collection<RegistrologinDTO> lista;


    public Collection<RegistrologinDTO> getLista() {
        RegistrologinDAO registrologinDAO = new RegistrologinDAO();
        lista = registrologinDAO.listar();
        return lista;
    }

    public RegistrologinDTO getRegistrologinDTO() {
        if (cargoDTO == null) {
            cargoDTO = new RegistrologinDTO();
        }
        return cargoDTO;
    }

    public void setRegistrologinDTO(RegistrologinDTO cargoDTO) {
        this.cargoDTO = cargoDTO;
    }


       /**
     *Instancia um registrologinDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        RegistrologinDAO registrologinDAO = new RegistrologinDAO();
        lista = registrologinDAO.listar();
        return "listar";
    }

      /**
     *Instancia um registrologinDAO, obtem o idCargo  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        RegistrologinDAO registrologinDAO = new RegistrologinDAO();
        getRegistrologinDTO().setIdLogin(new Integer(request.getParameter("idLogin")));
        registrologinDAO.excluir(getRegistrologinDTO());
        return "listar";
    }


     /**
     *Set IdLogin com zero e permiti inserir novo registro na tabela registrologin
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getRegistrologinDTO().setIdLogin(0);  // Cassiano
        return "inserir";
    }


     /**
     *Instancia um registrologinDAO, invoca o método gravar, passando  o objeto RegistrologinDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        RegistrologinDAO registrologinDAO = new RegistrologinDAO();
        registrologinDAO.gravar(getRegistrologinDTO());
        return "listar";
    }


     /**
     *Instancia um registrologinDAO, obtem o idLogino e invoca o método selecionar, passando  o objeto RegistrologinDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        RegistrologinDAO registrologinDAO = new RegistrologinDAO();
        getRegistrologinDTO().setIdLogin(new Integer(request.getParameter("idLogin")));
        setRegistrologinDTO(registrologinDAO.selecionar(getRegistrologinDTO()));
        return "inserir";
    }



    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String menu, para navegação.
     **/
    public String menu() {
        return "menu";
    }

}