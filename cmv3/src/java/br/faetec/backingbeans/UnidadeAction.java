/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.UnidadeDAO;
import br.faetec.model.dto.UnidadeDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *Métodos para ações relativas a  unidade, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class UnidadeAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UnidadeAction */
    public UnidadeAction() {
    }
    private UnidadeDTO unidadeDTO;
    private Collection<UnidadeDTO> lista;

    public Collection<UnidadeDTO> getLista() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        lista = unidadeDAO.listar();
        return lista;
    }

    public UnidadeDTO getUnidadeDTO() {
        if (unidadeDTO == null) {
            unidadeDTO = new UnidadeDTO();
        }
        return unidadeDTO;
    }

    public void setUnidadeDTO(UnidadeDTO unidadeDTO) {
        this.unidadeDTO = unidadeDTO;
    }

    /**
     *Instancia uma unidadeDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        lista = unidadeDAO.listar();
        return "listar";
    }

    /**
     *Instancia uma unidadeDAO, obtem o idunidade  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        getUnidadeDTO().setIdUnidade(new Integer(request.getParameter("idUnidade")));
        unidadeDAO.excluir(getUnidadeDTO());
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
     *Instancia uma unidadeDAO, invoca o método gravar, passando  o objeto UnidadeDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        unidadeDAO.gravar(getUnidadeDTO());
        return "listar";
    }

    /**
     *Instancia uma unidadeDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto UnidadeDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        getUnidadeDTO().setIdUnidade(new Integer(request.getParameter("idUnidade")));
        setUnidadeDTO(unidadeDAO.selecionar(getUnidadeDTO()));
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
