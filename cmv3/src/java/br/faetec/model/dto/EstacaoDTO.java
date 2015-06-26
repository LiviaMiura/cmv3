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
 *NamedQueries referentes a tabela estacao.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "estacao", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "EstacaoDTO.findAll", query = "SELECT e FROM EstacaoDTO e"),
    @NamedQuery(name = "EstacaoDTO.findByIdEstacao", query = "SELECT e FROM EstacaoDTO e WHERE e.idEstacao = :idEstacao"),
    @NamedQuery(name = "EstacaoDTO.findByCodigo", query = "SELECT e FROM EstacaoDTO e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "EstacaoDTO.findByDescricao", query = "SELECT e FROM EstacaoDTO e WHERE e.descricao = :descricao")})
public class EstacaoDTO extends Estacao implements Serializable {

   }

