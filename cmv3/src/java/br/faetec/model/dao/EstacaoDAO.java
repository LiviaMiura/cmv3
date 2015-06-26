/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.EstacaoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela estacao no banco de dados.
@author Antonio Cassiano
 **/
public class EstacaoDAO {

    /**
     * Recupera todos os registros da tabela estacao.
    @author Antonio Cassiano
    @return List - Lista preenchida com estacoes.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("EstacaoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela estacao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  estacaoDTO
    @return EstacaoDTO - preenchido com o registro selecionado.
     **/
    public EstacaoDTO selecionar(EstacaoDTO estacaoDTO) {
        return (EstacaoDTO) Database.manager.find(EstacaoDTO.class, estacaoDTO.getIdEstacao());
    }

    /**
     * Grava um registro na tabela estacao se IdEstacao igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  estacaoDTO
     **/
    public void gravar(EstacaoDTO estacaoDTO) {
        Database.manager.getTransaction().begin();
        if (estacaoDTO.getIdEstacao() == 0) {
            Database.manager.persist(estacaoDTO); // gravar
        } else {
            Database.manager.merge(estacaoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro na tabela Estacao
     * @param estacaoDTO
     */
    /**
     * Exclui um registro da tabela estacao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  estacaoDTO
     **/
    public void excluir(EstacaoDTO estacaoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(EstacaoDTO.class, estacaoDTO.getIdEstacao()));
        Database.manager.getTransaction().commit();

    }
}
