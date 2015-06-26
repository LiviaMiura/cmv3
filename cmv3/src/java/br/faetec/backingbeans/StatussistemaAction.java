/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.StatussistemaDAO;
import br.faetec.model.dto.StatussistemaDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas a statussistema, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class StatussistemaAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public StatussistemaAction() {
    }
    private StatussistemaDTO statussistemaDTO;
    private Collection<StatussistemaDTO> lista;

    public Collection<StatussistemaDTO> getLista() {
        StatussistemaDAO statussistemaDAO = new StatussistemaDAO();
        lista = statussistemaDAO.listar();
        return lista;
    }

    public StatussistemaDTO getStatussistemaDTO() {
        if (statussistemaDTO == null) {
            statussistemaDTO = new StatussistemaDTO();
        }
        return statussistemaDTO;
    }

    public void setStatussistemaDTO(StatussistemaDTO statussistemaDTO) {
        this.statussistemaDTO = statussistemaDTO;
    }

    /**
     *Instancia um statussistemaDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        StatussistemaDAO statussistemaDAO = new StatussistemaDAO();
        lista = statussistemaDAO.listar();
        return "listar";
    }

    /**
     *Instancia um statussistemaDAO, obtem o idstatussistema e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        StatussistemaDAO statussistemaDAO = new StatussistemaDAO();
        getStatussistemaDTO().setIdStatussistema(new Integer(request.getParameter("idStatussistema")));
        statussistemaDAO.excluir(getStatussistemaDTO());
        return "listar";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getStatussistemaDTO().setIdStatussistema(0);  // Cassiano
        return "inserir";
    }

    /**
     *Instancia um statussistemaDAO, invoca o método gravar, passando  o objeto statussistemaDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        StatussistemaDAO statussistemaDAO = new StatussistemaDAO();
        statussistemaDAO.gravar(getStatussistemaDTO());
        return "listar";
    }

    /**
     *Instancia um statussistemaDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto statussistemaDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        StatussistemaDAO statussistemaDAO = new StatussistemaDAO();
        getStatussistemaDTO().setIdStatussistema(new Integer(request.getParameter("idStatussistema")));
        setStatussistemaDTO(statussistemaDAO.selecionar(getStatussistemaDTO()));
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
