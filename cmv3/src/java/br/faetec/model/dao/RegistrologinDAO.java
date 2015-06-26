/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.RegistrologinDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação a tabela registrologin no banco de dados.
@author Antonio Cassiano
 **/
public class RegistrologinDAO {

    /**
     * Recupera todos os registros da tabela Registrologin
     * @return
     * @since   2011/03/2011
     */
    /**
     * Recupera todos os registros da tabela registrologin.
    @author Antonio Cassiano
    @return List - Lista preenchida com cargos.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("RegistrologinDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela registrologin, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  registrologinDTO
    @return RegistrologinDTO - preenchido com o registro selecionado.
     **/
    public RegistrologinDTO selecionar(RegistrologinDTO registrologinDTO) {
        return (RegistrologinDTO) Database.manager.find(RegistrologinDTO.class, registrologinDTO.getIdLogin());
    }

    /**
     * Grava um registro na tabela Registrologin.
    @author Antonio Cassiano
    @param  registrologinDTO
     **/
    public void gravar(RegistrologinDTO registrologinDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.persist(registrologinDTO); // gravar
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela registrologin, com base no valor da chave primária.
    @author Antonio Cassiano
     * @param registrologinDTO
     **/
    public void excluir(RegistrologinDTO registrologinDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(RegistrologinDTO.class, registrologinDTO.getIdLogin()));
        Database.manager.getTransaction().commit();

    }
}
