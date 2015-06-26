/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import br.faetec.model.dao.CargoDAO;
import br.faetec.model.dto.CargoDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para ações relativas ao cargo, get/set e strings para navegação.
@author Antonio Cassiano
 **/
public class CargoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of UsuarioAction */
    public CargoAction() {
    }
    private CargoDTO cargoDTO;
    private Collection<CargoDTO> lista;

    public Collection<CargoDTO> getLista() {
        CargoDAO cargoDAO = new CargoDAO();
        lista = cargoDAO.listar();
        return lista;
    }

    public CargoDTO getCargoDTO() {
        if (cargoDTO == null) {
            cargoDTO = new CargoDTO();
        }
        return cargoDTO;
    }

    public void setCargoDTO(CargoDTO cargoDTO) {
        this.cargoDTO = cargoDTO;
    }

    /**
     *Instancia um cargoDAO e invoca o método listar().
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String listar() {
        CargoDAO cargoDAO = new CargoDAO();
        lista = cargoDAO.listar();
        return "listar";
    }

    /**
     *Instancia um cargoDAO, obtem o idCargo  e invoca o método excluir
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String excluir() {
        CargoDAO cargoDAO = new CargoDAO();
        getCargoDTO().setIdCargo(new Integer(request.getParameter("idCargo")));
        cargoDAO.excluir(getCargoDTO());
        return "listar";
    }

    /**
     *Set IdCargo com zero e permiti inserir novo registro na tabela cargo
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String inserir() {
        getCargoDTO().setIdCargo(0);
        return "inserir";
    }

    /**
     *Instancia um cargoDAO, invoca o método gravar, passando  o objeto CargoDTO
    @author Antonio Cassiano
    @return String listar, para navegação.
     **/
    public String gravar() {
        CargoDAO cargoDAO = new CargoDAO();
        cargoDAO.gravar(getCargoDTO());
        return "listar";
    }

    /**
     *Instancia um cargoDAO, obtem o idCargo e invoca o método selecionar, passando  o objeto CargoDTO
    @author Antonio Cassiano
    @return String inserir, para navegação.
     **/
    public String alterar() {
        CargoDAO cargoDAO = new CargoDAO();
        getCargoDTO().setIdCargo(new Integer(request.getParameter("idCargo")));
        setCargoDTO(cargoDAO.selecionar(getCargoDTO()));
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
