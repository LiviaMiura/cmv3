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
 *NamedQueries referentes a tabela satelite.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "satelite", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "SateliteDTO.findAll", query = "SELECT s FROM SateliteDTO s"),
    @NamedQuery(name = "SateliteDTO.findByIdSatelite", query = "SELECT s FROM SateliteDTO s WHERE s.idSatelite = :idSatelite"),
    @NamedQuery(name = "SateliteDTO.findByCodigo", query = "SELECT s FROM SateliteDTO s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "SateliteDTO.findByDescricao", query = "SELECT s FROM SateliteDTO s WHERE s.descricao = :descricao")})
public class SateliteDTO extends Satelite implements Serializable {
   

}
