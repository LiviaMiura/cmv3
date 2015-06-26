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
 *NamedQueries referentes a tabela comando.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "comando", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "ComandoDTO.findAll", query = "SELECT c FROM ComandoDTO c"),
    @NamedQuery(name = "ComandoDTO.findByIdComando", query = "SELECT c FROM ComandoDTO c WHERE c.idComando = :idComando"),
    @NamedQuery(name = "ComandoDTO.findByTipo", query = "SELECT c FROM ComandoDTO c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "ComandoDTO.findBySubtipo", query = "SELECT c FROM ComandoDTO c WHERE c.subtipo = :subtipo"),
    @NamedQuery(name = "ComandoDTO.findByCodigo", query = "SELECT c FROM ComandoDTO c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "ComandoDTO.findByDescricao", query = "SELECT c FROM ComandoDTO c WHERE c.descricao = :descricao")})
public class ComandoDTO extends Comando implements Serializable {
   
}
