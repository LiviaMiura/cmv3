/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.MedidaDAO;
import br.faetec.model.dto.MedidaDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas a medida, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class MedidaAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public MedidaAction() {
    }
    private MedidaDTO medidaDTO;
    private Collection<MedidaDTO> lista;


    public Collection<MedidaDTO> getLista() {
        MedidaDAO medidaDAO = new MedidaDAO();
        lista = medidaDAO.listar();
        return lista;
    }

    public MedidaDTO getMedidaDTO() {
        if (medidaDTO == null) {
            medidaDTO = new MedidaDTO();
        }
        return medidaDTO;
    }

    public void setMedidaDTO(MedidaDTO medidaDTO) {
        this.medidaDTO = medidaDTO;
    }

    /**
     *Instancia uma medidaDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        MedidaDAO medidaDAO = new MedidaDAO();
        lista = medidaDAO.listar();
        return "listar";
    }

     /**
     *Instancia uma medidaDAO, obtem o idCargo  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        MedidaDAO medidaDAO = new MedidaDAO();
        getMedidaDTO().setIdMedida(new Integer(request.getParameter("idMedida")));
        medidaDAO.excluir(getMedidaDTO());
        return "listar";
    }

     /**
     *Set IdMedida com zero e permiti inserir novo registro na tabela medida
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getMedidaDTO().setIdMedida(0);  // Cassiano
        return "inserir";
    }

     /**
     *Instancia uma medidaDAO, invoca o método gravar, passando  o objeto MedidaDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        MedidaDAO medidaDAO = new MedidaDAO();
        medidaDAO.gravar(getMedidaDTO());
        return "listar";
    }

     /**
     *Instancia uma medidaDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto MedidaDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        MedidaDAO medidaDAO = new MedidaDAO();
        getMedidaDTO().setIdMedida(new Integer(request.getParameter("idMedida")));
        setMedidaDTO(medidaDAO.selecionar(getMedidaDTO()));
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