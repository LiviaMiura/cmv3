/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.StatushwDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela statushw no banco de dados.
@author Antonio Cassiano
 **/
public class StatushwDAO {

    /**
     * Recupera todos os registros da tabela statushw.
    @author Antonio Cassiano
    @return List - Lista preenchida com statushw.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("StatushwDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela statushw, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  statushwDTO
    @return statushwDTO - preenchido com o registro selecionado.
     **/
    public StatushwDTO selecionar(StatushwDTO statushwDTO) {
        return (StatushwDTO) Database.manager.find(StatushwDTO.class, statushwDTO.getIdStatushw());
    }

    /**
     * Grava um registro na tabela resultado se Idstatushw igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  statushwDTO
     **/
    public void gravar(StatushwDTO statushwDTO) {
        Database.manager.getTransaction().begin();
        if (statushwDTO.getIdStatushw() == 0) {
            Database.manager.persist(statushwDTO); // gravar
        } else {
            Database.manager.merge(statushwDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }
 
    /**
     * Grava um registro na tabela statushw.
    @author Antonio Cassiano
    @param  statushwDTO
     **/
    public void excluir(StatushwDTO statushwDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(StatushwDTO.class, statushwDTO.getIdStatushw()));
        Database.manager.getTransaction().commit();

    }
}
