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
 * Mapeamento da Superclass e métodos get/set referentes a tabela usuario.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
    @Column(name = "nome", length = 40)
    private String nome;
    @Basic(optional = false)
    @Column(name = "login", nullable = false, length = 40)
    private String login;
    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 40)
    private String senha;
    @Basic(optional = false)

     @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic(optional = false)


    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /*
     * 28/03  add DTO    ajuste dos nomes ########################################
     *
     */
    @JoinColumn(name = "cargo_id", referencedColumnName = "id_cargo", nullable = false)
    @ManyToOne(optional = false)
    private CargoDTO cargo;

    public CargoDTO getCargo() {
        if (cargo == null) {
            cargo = new CargoDTO();
        }
        return cargo;
    }

    public void setCargo(CargoDTO cargo) {
        this.cargo = cargo;
    }

    /*
     *  29/03  add DTO    ajuste dos nomes ########################################
     *
     */
    @JoinColumn(name = "unidade_id", referencedColumnName = "id_unidade", nullable = false)
    @ManyToOne(optional = false)
    private UnidadeDTO unidade;

    public UnidadeDTO getUnidade() {
        if (unidade == null) {
            unidade = new UnidadeDTO();
        }
        return unidade;
    }

    public void setUnidade(UnidadeDTO unidade) {
        this.unidade = unidade;
    }
    /*
     * fim do ajuste de nomes
     */

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String login, String senha, Date createdAt) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.createdAt = createdAt;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Usuario[idUsuario=" + idUsuario + "]";
    }
}
