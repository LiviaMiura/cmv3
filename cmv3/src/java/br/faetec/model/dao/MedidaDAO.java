/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.MedidaDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela medida no banco de dados.
@author Antonio Cassiano
 **/
public class MedidaDAO {

    /**
     * Recupera todos os registros da tabela medida.
    @author Antonio Cassiano
    @return List - Lista preenchida com medidas.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("MedidaDTO.findAll");

        query.setMaxResults(100);

        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela cargo, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  medidaDTO
    @return MedidaDTO - preenchido com o registro selecionado.
     **/
    public MedidaDTO selecionar(MedidaDTO medidaDTO) {
        return (MedidaDTO) Database.manager.find(MedidaDTO.class, medidaDTO.getIdMedida());
    }

    /**
     * Grava um registro na tabela medida.
    @author Antonio Cassiano
    @param  medidaDTO
     **/
    public void gravar(MedidaDTO medidaDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.persist(medidaDTO); // gravar
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela medida, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  medidaDTO
     **/
    public void excluir(MedidaDTO medidaDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(MedidaDTO.class, medidaDTO.getIdMedida()));
        Database.manager.getTransaction().commit();

    }
}
