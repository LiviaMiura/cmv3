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
 * Mapeamento da Superclass e métodos get/set referentes a tabela comando.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Comando implements Serializable {

    /**
     * Modificacoes:
     * 1. Collection<Requisicao> para  Collection<RequisicaoDTO>
     * 2. Get/set da  Collection<RequisicaoDTO>
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comando", nullable = false)
    private Integer idComando;
    @Basic(optional = false)
    @Column(name = "tipo", nullable = false)
    private int tipo;
    @Basic(optional = false)
    @Column(name = "subtipo", nullable = false)
    private int subtipo;
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false)
    private int codigo;
    @Column(name = "descricao", length = 40)
    private String descricao;

    /*
     * Relacionamento e nome do DTO
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comandoId")
    private Collection<RequisicaoDTO> requisicaoCollection;

    public Comando() {
    }

    public Comando(Integer idComando) {
        this.idComando = idComando;
    }

    public Comando(Integer idComando, int tipo, int subtipo, int codigo) {
        this.idComando = idComando;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.codigo = codigo;
    }

    public Integer getIdComando() {
        return idComando;
    }

    public void setIdComando(Integer idComando) {
        this.idComando = idComando;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(int subtipo) {
        this.subtipo = subtipo;
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

    public Collection<RequisicaoDTO> getRequisicaoCollection() {
        return requisicaoCollection;
    }

    public void setRequisicaoCollection(Collection<RequisicaoDTO> requisicaoCollection) {
        this.requisicaoCollection = requisicaoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComando != null ? idComando.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comando)) {
            return false;
        }
        Comando other = (Comando) object;
        if ((this.idComando == null && other.idComando != null) || (this.idComando != null && !this.idComando.equals(other.idComando))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Comando[idComando=" + idComando + "]";
    }
}
