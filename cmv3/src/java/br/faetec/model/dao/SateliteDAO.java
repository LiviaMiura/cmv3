/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.SateliteDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;




/**
 * Implementa os métodos para manipulação da tabela satelite no banco de dados.
@author Antonio Cassiano
 **/
public class SateliteDAO {

    /**
     * Recupera todos os registros da tabela satelite.
    @author Antonio Cassiano
    @return List - Lista preenchida com requisicões.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("SateliteDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

     /**
     * Recupera um registro da tabela satelite, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  sateliteDTO
    @return sateliteDTO - preenchido com o registro selecionado.
     **/
    public SateliteDTO selecionar(SateliteDTO sateliteDTO) {
        return (SateliteDTO) Database.manager.find(SateliteDTO.class, sateliteDTO.getIdSatelite());
    }

     /**
     * Grava um registro na tabela satelite se IdResultado igual a zero, caso contrario atualiza o registro.
    @author Antonio Cassiano
    @param sateliteDTO
     **/
    public void gravar(SateliteDTO sateliteDTO) {
        Database.manager.getTransaction().begin();
        if (sateliteDTO.getIdSatelite() == 0) {
            Database.manager.persist(sateliteDTO); // gravar
        } else {
            Database.manager.merge(sateliteDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

     /**
     * Exclui um registro da tabela satelite, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  sateliteDTO
     **/
    public void excluir(SateliteDTO sateliteDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(SateliteDTO.class, sateliteDTO.getIdSatelite()));
        Database.manager.getTransaction().commit();

    }


}
