/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.MissaoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

  /**
     * Implementa os métodos para manipulação da tabela missão no banco de dados.
    @author Antonio Cassiano
    **/
public class MissaoDAO {

    /**
     * Recupera todos os registros da tabela missao.
    @author Antonio Cassiano
    @return List - Lista preenchida com missao.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("MissaoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela missao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  missaoDTO
    @return MissaoDTO - preenchido com o registro selecionado.
     **/
    public MissaoDTO selecionar(MissaoDTO missaoDTO) {
        return (MissaoDTO) Database.manager.find(MissaoDTO.class, missaoDTO.getIdMissao());
    }

    /**
     * Grava um registro na tabela missao se IdMissao igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param  missaoDTO
     **/
    public void gravar(MissaoDTO missaoDTO) {
        Database.manager.getTransaction().begin();
        if (missaoDTO.getIdMissao() == 0) {
            Database.manager.persist(missaoDTO); // gravar
        } else {
            Database.manager.merge(missaoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

     /**
     * Exclui um registro da tabela missao, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  missaoDTO
     **/
    public void excluir(MissaoDTO missaoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(MissaoDTO.class, missaoDTO.getIdMissao()));
        Database.manager.getTransaction().commit();

    }
}
