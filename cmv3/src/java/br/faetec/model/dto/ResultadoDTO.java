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
 *NamedQueries referentes a tabela resultado.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "resultado", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "ResultadoDTO.findAll", query = "SELECT r FROM ResultadoDTO r"),
    @NamedQuery(name = "ResultadoDTO.findByIdResultado", query = "SELECT r FROM ResultadoDTO r WHERE r.idResultado = :idResultado"),
    @NamedQuery(name = "ResultadoDTO.findByTipo", query = "SELECT r FROM ResultadoDTO r WHERE r.tipo = :tipo"),
    @NamedQuery(name = "ResultadoDTO.findBySubtipo", query = "SELECT r FROM ResultadoDTO r WHERE r.subtipo = :subtipo"),
    @NamedQuery(name = "ResultadoDTO.findByTiporesposta", query = "SELECT r FROM ResultadoDTO r WHERE r.tiporesposta = :tiporesposta"),
    @NamedQuery(name = "ResultadoDTO.findByDescricao", query = "SELECT r FROM ResultadoDTO r WHERE r.descricao = :descricao")})
public class ResultadoDTO extends Resultado implements Serializable {
    
}
