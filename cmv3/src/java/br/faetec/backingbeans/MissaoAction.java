/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.MissaoDAO;
import br.faetec.model.dto.MissaoDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas a missao, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class MissaoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public MissaoAction() {
    }
    private MissaoDTO missaoDTO;
    private Collection<MissaoDTO> lista;


    public Collection<MissaoDTO> getLista() {
        MissaoDAO missaoDAO = new MissaoDAO();
        lista = missaoDAO.listar();
        return lista;
    }

    public MissaoDTO getMissaoDTO() {
        if (missaoDTO == null) {
            missaoDTO = new MissaoDTO();
        }
        return missaoDTO;
    }

    public void setMissaoDTO(MissaoDTO missaoDTO) {
        this.missaoDTO = missaoDTO;
    }

     /**
     *Instancia uma missaoDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        MissaoDAO missaoDAO = new MissaoDAO();
        lista = missaoDAO.listar();
        return "listar";
    }

        /**
     *Instancia uma missaoDAO, obtem o idCargo  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        MissaoDAO missaoDAO = new MissaoDAO();
        getMissaoDTO().setIdMissao(new Integer(request.getParameter("idMissao")));
        missaoDAO.excluir(getMissaoDTO());
        return "listar";
    }

        /**
     *Set IdMissao com zero e permiti inserir novo registro na tabela missao
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getMissaoDTO().setIdMissao(0);  // Cassiano
        return "inserir";
    }

      /**
     *Instancia uma missaoDAO, invoca o método gravar, passando  o objeto MissaoDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        MissaoDAO missaoDAO = new MissaoDAO();
        missaoDAO.gravar(getMissaoDTO());
        return "listar";
    }
  /**
     *Instancia uma missaoDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto MissaoDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        MissaoDAO missaoDAO = new MissaoDAO();
        getMissaoDTO().setIdMissao(new Integer(request.getParameter("idMissao")));
        setMissaoDTO(missaoDAO.selecionar(getMissaoDTO()));
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