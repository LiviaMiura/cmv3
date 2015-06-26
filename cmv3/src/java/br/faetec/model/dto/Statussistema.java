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
 * Mapeamento da Superclass e métodos get/set referentes a tabela statusistema.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Statussistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_statussistema", nullable = false)
    private Integer idStatussistema;
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false)
    private int codigo;
    @Column(name = "descricao", length = 40)
    private String descricao;

    /*
     * Relacionamento e nome do DTO
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statussistemaId")
    private Collection<RespostaDTO> respostaCollection;

    public Statussistema() {
    }

    public Statussistema(Integer idStatussistema) {
        this.idStatussistema = idStatussistema;
    }

    public Statussistema(Integer idStatussistema, int codigo) {
        this.idStatussistema = idStatussistema;
        this.codigo = codigo;
    }

    public Integer getIdStatussistema() {
        return idStatussistema;
    }

    public void setIdStatussistema(Integer idStatussistema) {
        this.idStatussistema = idStatussistema;
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

    public Collection<RespostaDTO> getRespostaCollection() {
        return respostaCollection;
    }

    public void setRespostaCollection(Collection<RespostaDTO> respostaCollection) {
        this.respostaCollection = respostaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatussistema != null ? idStatussistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statussistema)) {
            return false;
        }
        Statussistema other = (Statussistema) object;
        if ((this.idStatussistema == null && other.idStatussistema != null) || (this.idStatussistema != null && !this.idStatussistema.equals(other.idStatussistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Statussistema[idStatussistema=" + idStatussistema + "]";
    }

}
