/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.StatussistemaDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;



/**
 * Implementa os métodos para manipulação da tabela statussistema no banco de dados.
@author Antonio Cassiano
 **/
public class StatussistemaDAO {


      /**
     * Recupera todos os registros da tabela statussistema.
    @author Antonio Cassiano
    @return List - Lista preenchida com statussistema.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("StatussistemaDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }


      /**
     * Recupera um registro da tabela requisicao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  statussistemaDTO
    @return statussistemaDTO - preenchido com o registro selecionado.
     **/
    public StatussistemaDTO selecionar(StatussistemaDTO statussistemaDTO) {
        return (StatussistemaDTO) Database.manager.find(StatussistemaDTO.class, statussistemaDTO.getIdStatussistema());
    }



    /**
     * Grava um registro na tabela resultado se IIdStatussistema igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  statussistemaDTO
     **/
    public void gravar(StatussistemaDTO statussistemaDTO) {
        Database.manager.getTransaction().begin();
        if (statussistemaDTO.getIdStatussistema() == 0) {
            Database.manager.persist(statussistemaDTO); // gravar
        } else {
            Database.manager.merge(statussistemaDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }
 
      /**
     * Exclui um registro da tabela statussistema, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  statussistemaDTO
     **/
    public void excluir(StatussistemaDTO statussistemaDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(StatussistemaDTO.class, statussistemaDTO.getIdStatussistema()));
        Database.manager.getTransaction().commit();

    }


}
