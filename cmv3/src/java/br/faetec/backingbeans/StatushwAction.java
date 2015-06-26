/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.StatushwDAO;
import br.faetec.model.dto.StatushwDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas ao statushw, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class StatushwAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public StatushwAction() {
    }
    private StatushwDTO statushwDTO;
    private Collection<StatushwDTO> lista;

    public Collection<StatushwDTO> getLista() {
        StatushwDAO statushwDAO = new StatushwDAO();
        lista = statushwDAO.listar();
        return lista;
    }

    public StatushwDTO getStatushwDTO() {
        if (statushwDTO == null) {
            statushwDTO = new StatushwDTO();
        }
        return statushwDTO;
    }

    public void setStatushwDTO(StatushwDTO statushwDTO) {
        this.statushwDTO = statushwDTO;
    }

    /**
     *Instancia um statushwDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        StatushwDAO statushwDAO = new StatushwDAO();
        lista = statushwDAO.listar();
        return "listar";
    }

    /**
     *Instancia um statushwDAO, obtem o idCargo  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        StatushwDAO statushwDAO = new StatushwDAO();
        getStatushwDTO().setIdStatushw(new Integer(request.getParameter("idStatushw")));
        statushwDAO.excluir(getStatushwDTO());
        return "listar";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        return "inserir";
    }

    /**
     *Instancia um statushwDAO, invoca o método gravar, passando  o objeto StatushwDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        StatushwDAO statushwDAO = new StatushwDAO();
        statushwDAO.gravar(getStatushwDTO());
        return "listar";
    }

    /**
     *Instancia um statushwDAO, obtem o idStatushw e invoca o método selecionar, passando  o objeto StatushwDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        StatushwDAO statushwDAO = new StatushwDAO();
        getStatushwDTO().setIdStatushw(new Integer(request.getParameter("idStatushw")));
        setStatushwDTO(statushwDAO.selecionar(getStatushwDTO()));
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
