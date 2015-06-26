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
 * Mapeamento da Superclass e métodos get/set referentes a tabela unidade.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Unidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_unidade", nullable = false)
    private Integer idUnidade;
    @Column(name = "descricao", length = 40)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidade")
    private Collection<UsuarioDTO> usuarioCollection;

    //  28/06  add DTO

    public Unidade() {
    }

    public Unidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

     //  28/06  add DTO
    public Collection<UsuarioDTO> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<UsuarioDTO> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidade != null ? idUnidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidade)) {
            return false;
        }
        Unidade other = (Unidade) object;
        if ((this.idUnidade == null && other.idUnidade != null) || (this.idUnidade != null && !this.idUnidade.equals(other.idUnidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Unidade[idUnidade=" + idUnidade + "]";
    }

}
