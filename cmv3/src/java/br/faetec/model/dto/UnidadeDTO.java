/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *NamedQueries referentes a tabela unidade.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "unidade", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "UnidadeDTO.findAll", query = "SELECT u FROM UnidadeDTO u"),
    @NamedQuery(name = "UnidadeDTO.findByIdUnidade", query = "SELECT u FROM UnidadeDTO u WHERE u.idUnidade = :idUnidade"),
    @NamedQuery(name = "UnidadeDTO.findByDescricao", query = "SELECT u FROM UnidadeDTO u WHERE u.descricao = :descricao")})
public class UnidadeDTO extends Unidade implements Serializable {
}
