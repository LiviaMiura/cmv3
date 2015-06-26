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
 * Mapeamento da Superclass e métodos get/set referentes a tabela missao.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Missao implements Serializable {
/**
 * Modificacoes:
 * 1. Collection<Requisicao> para  Collection<RequisicaoDTO>
 * 2. Get/set da  Collection<RequisicaoDTO>
 */


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_missao", nullable = false)
    private Integer idMissao;
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false)
    private int codigo;
    @Column(name = "descricao", length = 40)
    private String descricao;
    /*
     * Relacionamento e nome do DTO
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "missaoId")
    private Collection<RequisicaoDTO> requisicaoCollection;

    public Missao() {
    }

    public Missao(Integer idMissao) {
        this.idMissao = idMissao;
    }

    public Missao(Integer idMissao, int codigo) {
        this.idMissao = idMissao;
        this.codigo = codigo;
    }

    public Integer getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(Integer idMissao) {
        this.idMissao = idMissao;
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
        hash += (idMissao != null ? idMissao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Missao)) {
            return false;
        }
        Missao other = (Missao) object;
        if ((this.idMissao == null && other.idMissao != null) || (this.idMissao != null && !this.idMissao.equals(other.idMissao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Missao[idMissao=" + idMissao + "]";
    }
}
