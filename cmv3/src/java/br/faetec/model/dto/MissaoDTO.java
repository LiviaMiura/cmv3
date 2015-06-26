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
 *NamedQueries referentes a tabela missao.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "missao", catalog = "cmv3", schema = "")
@NamedQueries({
    @NamedQuery(name = "MissaoDTO.findAll", query = "SELECT m FROM MissaoDTO m"),
    @NamedQuery(name = "MissaoDTO.findByIdMissao", query = "SELECT m FROM MissaoDTO m WHERE m.idMissao = :idMissao"),
    @NamedQuery(name = "MissaoDTO.findByCodigo", query = "SELECT m FROM MissaoDTO m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "MissaoDTO.findByDescricao", query = "SELECT m FROM MissaoDTO m WHERE m.descricao = :descricao")})
public class MissaoDTO extends Missao implements Serializable {
}
