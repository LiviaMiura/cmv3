/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 /**
     * Pacote br.faetec.model.dao
    @author Antonio Cassiano
    **/
package br.faetec.model.dao;

import br.faetec.model.dto.CargoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

  /**
     * Implementa os métodos para manipulação da tabela cargo no banco de dados.
    @author Antonio Cassiano
    **/
public class CargoDAO {

    /**
     * Recupera todos os registros da tabela cargo.
    @author Antonio Cassiano
    @return List - Lista preenchida com cargos.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("CargoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela cargo, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  cargoDTO
    @return CargoDTO - preenchido com o registro selecionado.
     **/
    public CargoDTO selecionar(CargoDTO cargoDTO) {
        return (CargoDTO) Database.manager.find(CargoDTO.class, cargoDTO.getIdCargo());
    }

    /**
     * Grava um registro na tabela cargo se Idcargo igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  cargoDTO
     **/
    public void gravar(CargoDTO cargoDTO) {
        Database.manager.getTransaction().begin();
        if (cargoDTO.getIdCargo() == 0) {
            Database.manager.persist(cargoDTO); // gravar
        } else {
            Database.manager.merge(cargoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela cargo, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  cargoDTO
     **/
    public void excluir(CargoDTO cargoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(CargoDTO.class, cargoDTO.getIdCargo()));
        Database.manager.getTransaction().commit();

    }
}
