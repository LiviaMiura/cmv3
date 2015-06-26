/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.ResultadoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela resultado no banco de dados.
@author Antonio Cassiano
 **/
public class ResultadoDAO {

    /**
     * Recupera todos os registros da tabela resultado.
    @author Antonio Cassiano
    @return List - Lista preenchida com requisicões.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("ResultadoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela selecionar, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  resultadoDTO
    @return resultadoDTO - preenchido com o registro selecionado.
     **/
    public ResultadoDTO selecionar(ResultadoDTO resultadoDTO) {
        return (ResultadoDTO) Database.manager.find(ResultadoDTO.class, resultadoDTO.getIdResultado());
    }

    /**
     * Grava um registro na tabela resultado se IdResultado igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  resultadoDTO
     **/
    public void gravar(ResultadoDTO resultadoDTO) {
        Database.manager.getTransaction().begin();
        if (resultadoDTO.getIdResultado() == 0) {
            Database.manager.persist(resultadoDTO); // gravar
        } else {
            Database.manager.merge(resultadoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela requisicao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param   resultadoDTO
     **/
    public void excluir(ResultadoDTO resultadoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(ResultadoDTO.class, resultadoDTO.getIdResultado()));
        Database.manager.getTransaction().commit();

    }
}
