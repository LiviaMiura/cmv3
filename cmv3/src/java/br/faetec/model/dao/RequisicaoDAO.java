/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.RequisicaoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela requisicao no banco de dados.
@author Antonio Cassiano
 **/
public class RequisicaoDAO {

    /**
     * Recupera todos os registros da tabela requisicao.
    @author Antonio Cassiano
    @return List - Lista preenchida com requisicões.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("RequisicaoDTO.findAll");

        query.setMaxResults(10);

        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela requisicao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  requisicaoDTO
    @return RequisicaoDTO - preenchido com o registro selecionado.
     **/
    public RequisicaoDTO selecionar(RequisicaoDTO requisicaoDTO) {
        return (RequisicaoDTO) Database.manager.find(RequisicaoDTO.class, requisicaoDTO.getIdRequisicao());
    }

    /**
     * Grava um registro na tabela requisicao.
    @author Antonio Cassiano
    @param  requisicaoDTO
     **/
    public void gravar(RequisicaoDTO requisicaoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.persist(requisicaoDTO); // gravar
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela requisicao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  requisicaoDTO
     **/
    public void excluir(RequisicaoDTO requisicaoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(RequisicaoDTO.class, requisicaoDTO.getIdRequisicao()));
        Database.manager.getTransaction().commit();

    }
}
