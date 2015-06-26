/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.RespostaDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela resposta no banco de dados.
@author Antonio Cassiano
 **/
public class RespostaDAO {

    /**
     * Recupera todos os registros da tabela resposta.
    @author Antonio Cassiano
    @return List - Lista preenchida com requisicões.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("RespostaDTO.findAll");

        //   query.setMaxResults(10); //teste com 10

        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela selecionar, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  respostaDTO
    @return respostaDTO - preenchido com o registro selecionado.
     **/
    public RespostaDTO selecionar(RespostaDTO respostaDTO) {
        return (RespostaDTO) Database.manager.find(RespostaDTO.class, respostaDTO.getIdResposta());
    }

    /**
     * Grava um registro na tabela resposta.
    @author Antonio Cassiano
    @param  respostaDTO
     **/
    public void gravar(RespostaDTO respostaDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.persist(respostaDTO); // gravar
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela requisicao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  respostaDTO
     **/
    public void excluir(RespostaDTO respostaDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(RespostaDTO.class, respostaDTO.getIdResposta()));
        Database.manager.getTransaction().commit();

    }
}
