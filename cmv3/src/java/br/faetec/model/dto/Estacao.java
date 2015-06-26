/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * Mapeamento da Superclass e métodos get/set referentes a tabela cargo.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Estacao implements Serializable {

    /**
     *  Modificacoes:
     * 1. Collection<Requisicao> para  Collection<RequisicaoDTO>
     * 2. Get/set da  Collection<RequisicaoDTO>
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estacao", nullable = false)
    private Integer idEstacao;
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false)
    private int codigo;
    @Column(name = "descricao", length = 40)
    private String descricao;
    /*
     * Relacionamento e nome do DTO
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estacaoId")
    private Collection<RequisicaoDTO> requisicaoCollection;

    public Estacao() {
    }

    public Estacao(Integer idEstacao) {
        this.idEstacao = idEstacao;
    }

    public Estacao(Integer idEstacao, int codigo) {
        this.idEstacao = idEstacao;
        this.codigo = codigo;
    }

    public Integer getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(Integer idEstacao) {
        this.idEstacao = idEstacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /*
     * Add DTO
     */
    public Collection<RequisicaoDTO> getRequisicaoCollection() {
        return requisicaoCollection;
    }

    public void setRequisicaoCollection(Collection<RequisicaoDTO> requisicaoCollection) {
        this.requisicaoCollection = requisicaoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstacao != null ? idEstacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estacao)) {
            return false;
        }
        Estacao other = (Estacao) object;
        if ((this.idEstacao == null && other.idEstacao != null) || (this.idEstacao != null && !this.idEstacao.equals(other.idEstacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Estacao[idEstacao=" + idEstacao + "]";
    }
}
