/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.ComandoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

  /**
     * Implementa os métodos para manipulação da tabela comando no banco de dados.
    @author Antonio Cassiano
    **/
public class ComandoDAO {

    /**
     * Recupera todos os registros da tabela comando.
    @author Antonio Cassiano
    @return List - Lista preenchida com comandos.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("ComandoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela comando, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  comandoDTO
    @return ComandoDTO - preenchido com o registro selecionado.
     **/
    public ComandoDTO selecionar(ComandoDTO comandoDTO) {
        return (ComandoDTO) Database.manager.find(ComandoDTO.class, comandoDTO.getIdComando());
    }

    /**
     * Grava um registro na tabela comando se Idcomando igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  comandoDTO
     **/
    public void gravar(ComandoDTO comandoDTO) {
        Database.manager.getTransaction().begin();
        if (comandoDTO.getIdComando() == 0) {
            Database.manager.persist(comandoDTO); // gravar
        } else {
            Database.manager.merge(comandoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela comando, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  comandoDTO
     **/
    public void excluir(ComandoDTO comandoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(ComandoDTO.class, comandoDTO.getIdComando()));
        Database.manager.getTransaction().commit();

    }
}
