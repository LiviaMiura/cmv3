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
 * Mapeamento da Superclass e métodos get/set referentes a tabela statushw.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Statushw implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_statushw", nullable = false)
    private Integer idStatushw;
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false)
    private int codigo;
    @Column(name = "descricao", length = 40)
    private String descricao;


     /*
     * Relacionamento e nome do DTO
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statushwId")
    private Collection<RespostaDTO> respostaCollection;


    public Statushw() {
    }

    public Statushw(Integer idStatushw) {
        this.idStatushw = idStatushw;
    }

    public Statushw(Integer idStatushw, int codigo) {
        this.idStatushw = idStatushw;
        this.codigo = codigo;
    }

    public Integer getIdStatushw() {
        return idStatushw;
    }

    public void setIdStatushw(Integer idStatushw) {
        this.idStatushw = idStatushw;
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
        hash += (idStatushw != null ? idStatushw.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statushw)) {
            return false;
        }
        Statushw other = (Statushw) object;
        if ((this.idStatushw == null && other.idStatushw != null) || (this.idStatushw != null && !this.idStatushw.equals(other.idStatushw))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Statushw[idStatushw=" + idStatushw + "]";
    }

}
