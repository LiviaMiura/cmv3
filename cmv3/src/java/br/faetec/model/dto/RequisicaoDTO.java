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
import javax.persistence.Transient;

/**
 *NamedQueries referentes a tabela requisicao.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "requisicao", catalog = "cmv3", schema = "")
@NamedQueries({
    //@NamedQuery(name = "RequisicaoDTO.findAll", query = "SELECT r FROM RequisicaoDTO r"),

    @NamedQuery(name = "RequisicaoDTO.findAll", query = "SELECT r FROM RequisicaoDTO r order by r.idRequisicao desc"),

    @NamedQuery(name = "RequisicaoDTO.findByIdRequisicao", query = "SELECT r FROM RequisicaoDTO r WHERE r.idRequisicao = :idRequisicao"),
    @NamedQuery(name = "RequisicaoDTO.findByGrupos", query = "SELECT r FROM RequisicaoDTO r WHERE r.grupos = :grupos"),
    @NamedQuery(name = "RequisicaoDTO.findByDatacao", query = "SELECT r FROM RequisicaoDTO r WHERE r.datacao = :datacao")})

    public class RequisicaoDTO extends Requisicao implements Serializable {

    @Transient   
    private int taxaAquisicao;

    public int getTaxaAquisicao() {
        return taxaAquisicao;
    }

    public void setTaxaAquisicao(int taxaAquisicao) {
        this.taxaAquisicao = taxaAquisicao;
    }
  
    @Transient   
    private int tipoAquisicao;

    public int getTipoAquisicao() {
        return tipoAquisicao;
    }

    public void setTipoAquisicao(int tipoAquisicao) {
        this.tipoAquisicao = tipoAquisicao;
    }


     @Transient
    private boolean statusHWAtual;

    public boolean isStatusHWAtual() {
        return statusHWAtual;
    }

    public void setStatusHWAtual(boolean statusHWAtual) {
        this.statusHWAtual = statusHWAtual;
    }

   
      @Transient
    private boolean statusSistemaMedida;

    public boolean isStatusSistemaMedida() {
        return statusSistemaMedida;
    }

    public void setStatusSistemaMedida(boolean statusSistemaMedida) {
        this.statusSistemaMedida = statusSistemaMedida;
    }

      

   
    }
