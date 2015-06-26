/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.UnidadeDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela unidade no banco de dados.
@author Antonio Cassiano
 **/
public class UnidadeDAO {

    /**
     * Recupera todos os registros da tabela unidade.
    @author Antonio Cassiano
    @return List - Lista preenchida com unidades.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("UnidadeDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela unidade, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  unidadeDTO
    @return UnidadeDTO - preenchido com o registro selecionado.
     **/
    public UnidadeDTO selecionar(UnidadeDTO unidadeDTO) {
        return (UnidadeDTO) Database.manager.find(UnidadeDTO.class, unidadeDTO.getIdUnidade());
    }

    /**
     * Grava um registro na tabela unidade se IdUnidade igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  unidadeDTO
     **/
    public void gravar(UnidadeDTO unidadeDTO) {
        Database.manager.getTransaction().begin();
        if (unidadeDTO.getIdUnidade() == 0) {
            Database.manager.persist(unidadeDTO); // gravar
        } else {
            Database.manager.merge(unidadeDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela unidade, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  unidadeDTO
     **/
    public void excluir(UnidadeDTO unidadeDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(UnidadeDTO.class, unidadeDTO.getIdUnidade()));
        Database.manager.getTransaction().commit();

    }
}
