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
 *NamedQueries referentes a tabela resposta.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "resposta", catalog = "cmv3", schema = "")
@NamedQueries({
    //@NamedQuery(name = "RespostaDTO.findAll", query = "SELECT r FROM RespostaDTO r"),
    @NamedQuery(name = "RespostaDTO.findAll", query = "SELECT r FROM RespostaDTO r order by r.idResposta desc"),
   
    @NamedQuery(name = "RespostaDTO.findByIdResposta", query = "SELECT r FROM RespostaDTO r WHERE r.idResposta = :idResposta"),
    @NamedQuery(name = "RespostaDTO.findByMensagem", query = "SELECT r FROM RespostaDTO r WHERE r.mensagem = :mensagem"),
    @NamedQuery(name = "RespostaDTO.findByAmostras", query = "SELECT r FROM RespostaDTO r WHERE r.amostras = :amostras"),
    @NamedQuery(name = "RespostaDTO.findByDatacao", query = "SELECT r FROM RespostaDTO r WHERE r.datacao = :datacao")})
public class RespostaDTO extends Resposta implements Serializable {

}
