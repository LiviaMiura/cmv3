/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.ComandoDAO;
import br.faetec.model.dto.ComandoDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas ao comando, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class ComandoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of ComandoAction */
    public ComandoAction() {
    }
    private ComandoDTO comandoDTO;
    private Collection<ComandoDTO> lista;

    public Collection<ComandoDTO> getLista() {
        ComandoDAO comandoDAO = new ComandoDAO();
        lista = comandoDAO.listar();
        return lista;
    }

    public ComandoDTO getComandoDTO() {
        if (comandoDTO == null) {
            comandoDTO = new ComandoDTO();
        }
        return comandoDTO;
    }

    public void setComandoDTO(ComandoDTO comandoDTO) {
        this.comandoDTO = comandoDTO;
    }

    /**
     *Instancia um comandoDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        ComandoDAO comandoDAO = new ComandoDAO();
        lista = comandoDAO.listar();
        return "listar";
    }

    /**
     *Instancia um comandoDAO, obtem o idComando  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        ComandoDAO comandoDAO = new ComandoDAO();
        getComandoDTO().setIdComando(new Integer(request.getParameter("idComando")));
        comandoDAO.excluir(getComandoDTO());
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
     *Instancia um comandoDAO, invoca o método gravar, passando  o objeto ComandoDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        ComandoDAO comandoDAO = new ComandoDAO();
        comandoDAO.gravar(getComandoDTO());
        return "listar";
    }

    /**
     *Instancia um comandoDAO, obtem o idComando e invoca o método selecionar, passando o objeto ComandoDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        ComandoDAO comandoDAO = new ComandoDAO();
        getComandoDTO().setIdComando(new Integer(request.getParameter("idComando")));
        setComandoDTO(comandoDAO.selecionar(getComandoDTO()));
        return "inserir";
    }

    /**
     *Controle de navegação
    @author Antonio Cassiano
    @return String menu, para navegação.
     **/
    public String menu() {
        return "menu";
    }
}
