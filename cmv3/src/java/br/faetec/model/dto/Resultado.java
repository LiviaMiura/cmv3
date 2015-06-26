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
 * Mapeamento da Superclass e métodos get/set referentes a tabela resultado.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Resultado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resultado", nullable = false)
    private Integer idResultado;
    @Basic(optional = false)
    @Column(name = "tipo", nullable = false)
    private int tipo;
    @Basic(optional = false)
    @Column(name = "subtipo", nullable = false)
    private int subtipo;
    @Basic(optional = false)
    @Column(name = "tiporesposta", nullable = false)
    private int tiporesposta;
    @Column(name = "descricao", length = 40)
    private String descricao;

     /*
     * Relacionamento e nome do DTO
     *
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resultadoId")
    private Collection<RespostaDTO> respostaCollection;

    public Resultado() {
    }

    public Resultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public Resultado(Integer idResultado, int tipo, int subtipo, int tiporesposta) {
        this.idResultado = idResultado;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.tiporesposta = tiporesposta;
    }

    public Integer getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Integer idResultado) {
        this.idResultado = idResultado;
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

    public int getTiporesposta() {
        return tiporesposta;
    }

    public void setTiporesposta(int tiporesposta) {
        this.tiporesposta = tiporesposta;
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
        hash += (idResultado != null ? idResultado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resultado)) {
            return false;
        }
        Resultado other = (Resultado) object;
        if ((this.idResultado == null && other.idResultado != null) || (this.idResultado != null && !this.idResultado.equals(other.idResultado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Resultado[idResultado=" + idResultado + "]";
    }

}
