/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faetec.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author pedroquina
 */


/**
 * Implementa as funcionalidades de conexao com o banco de dados.
@author Antonio Cassiano
 **/
public class Database {
       public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("conexaoBancoDados");  // persistence.xml
       public static EntityManager manager = factory.createEntityManager();


}
