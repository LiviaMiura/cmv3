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
 *NamedQueries referentes a tabela medida.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "medida", catalog = "cmv3", schema = "")
@NamedQueries({

  //  @NamedQuery(name = "MedidaDTO.findAll", query = "SELECT m FROM MedidaDTO m"),

     @NamedQuery(name = "MedidaDTO.findAll", query = "SELECT m FROM MedidaDTO m order by m.idMedida desc"),
   //   @NamedQuery(name = "RequisicaoDTO.findAll", query = "SELECT r FROM RequisicaoDTO r order by r.idRequisicao desc"),

    @NamedQuery(name = "MedidaDTO.findByIdMedida", query = "SELECT m FROM MedidaDTO m WHERE m.idMedida = :idMedida"),
    @NamedQuery(name = "MedidaDTO.findByMensagem", query = "SELECT m FROM MedidaDTO m WHERE m.mensagem = :mensagem"),
    
     @NamedQuery(name = "MedidaDTO.findByUtc", query = "SELECT m FROM MedidaDTO m WHERE m.utc = :utc"),

    @NamedQuery(name = "MedidaDTO.findByTotalizador", query = "SELECT m FROM MedidaDTO m WHERE m.totalizador = :totalizador"),
    @NamedQuery(name = "MedidaDTO.findByIntervalo", query = "SELECT m FROM MedidaDTO m WHERE m.intervalo = :intervalo"),
    @NamedQuery(name = "MedidaDTO.findByFrequencia", query = "SELECT m FROM MedidaDTO m WHERE m.frequencia = :frequencia")})
public class MedidaDTO extends Medida implements Serializable {

}
