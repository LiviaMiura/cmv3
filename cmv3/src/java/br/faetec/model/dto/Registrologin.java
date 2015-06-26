/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * Mapeamento da Superclass e métodos get/set referentes a tabela registrologin.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Registrologin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_login", nullable = false)
    private Integer idLogin;
    @Basic(optional = false)

    @Column(name = "dataCriacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;  // mesmo nome da tabela registrologin

    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", nullable = false)
    @ManyToOne(optional = false)
    private UsuarioDTO usuarioId;
    //########################################################################
    //Ajuste de  nomes DTO

    public UsuarioDTO getUsuarioId() {
        if (usuarioId == null) {
            usuarioId = new UsuarioDTO();
        }
        return usuarioId;
    }

    public void setUsuarioId(UsuarioDTO usuarioId){
        this.usuarioId = usuarioId;
    }
 //###########################################################################
    
    public Registrologin() {
    }

    public Registrologin(Integer idLogin) {
        this.idLogin = idLogin;
    }

    public Registrologin(Integer idLogin, Date dataCriacao) {
        this.idLogin = idLogin;
        this.dataCriacao = dataCriacao;
    }

    public Integer getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Integer idLogin) {
        this.idLogin = idLogin;
    }

  
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }



   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogin != null ? idLogin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registrologin)) {
            return false;
        }
        Registrologin other = (Registrologin) object;
        if ((this.idLogin == null && other.idLogin != null) || (this.idLogin != null && !this.idLogin.equals(other.idLogin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Registrologin[idLogin=" + idLogin + "]";
    }
}
