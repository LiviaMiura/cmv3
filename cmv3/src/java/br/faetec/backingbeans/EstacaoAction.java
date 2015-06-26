/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.EstacaoDAO;
import br.faetec.model.dto.EstacaoDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas a estacao, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class EstacaoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public EstacaoAction() {
    }
    private EstacaoDTO estacaoDTO;
    private Collection<EstacaoDTO> lista;

    public Collection<EstacaoDTO> getLista() {
        EstacaoDAO estacaoDAO = new EstacaoDAO();
        lista = estacaoDAO.listar();
        return lista;
    }

    public EstacaoDTO getEstacaoDTO() {
        if (estacaoDTO == null) {
            estacaoDTO = new EstacaoDTO();
        }
        return estacaoDTO;
    }

    public void setEstacaoDTO(EstacaoDTO estacaoDTO) {
        this.estacaoDTO = estacaoDTO;
    }

    /**
     *Instancia um estacaoDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        EstacaoDAO estacaoDAO = new EstacaoDAO();
        lista = estacaoDAO.listar();
        return "listar";
    }

    /**
     *Instancia um estacaoDAO, obtem o idCargo  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        EstacaoDAO estacaoDAO = new EstacaoDAO();
        getEstacaoDTO().setIdEstacao(new Integer(request.getParameter("idEstacao")));
        estacaoDAO.excluir(getEstacaoDTO());
        return "listar";
    }

     /**
     *Controle de navegação
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        return "inserir";
    }

    /**
     *Instancia um estacaoDAO, invoca o método gravar, passando  o objeto estacaoDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        EstacaoDAO estacaoDAO = new EstacaoDAO();
        estacaoDAO.gravar(getEstacaoDTO());
        return "listar";
    }

    /**
     *Instancia um estacaoDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto estacaoDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        EstacaoDAO estacaoDAO = new EstacaoDAO();
        getEstacaoDTO().setIdEstacao(new Integer(request.getParameter("idEstacao")));
        setEstacaoDTO(estacaoDAO.selecionar(getEstacaoDTO()));
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
