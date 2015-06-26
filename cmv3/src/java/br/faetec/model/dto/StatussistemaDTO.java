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
 *NamedQueries referentes a tabela statussistema.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "statussistema", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "StatussistemaDTO.findAll", query = "SELECT s FROM StatussistemaDTO s"),
    @NamedQuery(name = "StatussistemaDTO.findByIdStatussistema", query = "SELECT s FROM StatussistemaDTO s WHERE s.idStatussistema = :idStatussistema"),
    @NamedQuery(name = "StatussistemaDTO.findByCodigo", query = "SELECT s FROM StatussistemaDTO s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "StatussistemaDTO.findByDescricao", query = "SELECT s FROM StatussistemaDTO s WHERE s.descricao = :descricao")})
public class StatussistemaDTO extends Statussistema implements Serializable {

}
