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
 *NamedQueries referentes a tabela statushw.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "statushw", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "StatushwDTO.findAll", query = "SELECT s FROM StatushwDTO s"),
    @NamedQuery(name = "StatushwDTO.findByIdStatushw", query = "SELECT s FROM StatushwDTO s WHERE s.idStatushw = :idStatushw"),
    @NamedQuery(name = "StatushwDTO.findByCodigo", query = "SELECT s FROM StatushwDTO s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "StatushwDTO.findByDescricao", query = "SELECT s FROM StatushwDTO s WHERE s.descricao = :descricao")})
public class StatushwDTO extends Statushw implements Serializable {

}
