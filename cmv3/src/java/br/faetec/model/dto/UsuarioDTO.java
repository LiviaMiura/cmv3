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
import javax.persistence.UniqueConstraint;

/**
 *NamedQueries referentes a tabela usuario.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "usuario", catalog = "cmv3", schema = "",
uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})})

@NamedQueries({
    @NamedQuery(name = "UsuarioDTO.findAll", query = "SELECT u FROM UsuarioDTO u"),
    @NamedQuery(name = "UsuarioDTO.findByIdUsuario", query = "SELECT u FROM UsuarioDTO u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "UsuarioDTO.findByNome", query = "SELECT u FROM UsuarioDTO u WHERE u.nome = :nome"),
    @NamedQuery(name = "UsuarioDTO.findByLogin", query = "SELECT u FROM UsuarioDTO u WHERE u.login = :login"),
    @NamedQuery(name = "UsuarioDTO.findBySenha", query = "SELECT u FROM UsuarioDTO u WHERE u.senha = :senha"),
    @NamedQuery(name = "UsuarioDTO.findByEmail", query = "SELECT u FROM UsuarioDTO u WHERE u.email = :email"),
    @NamedQuery(name = "UsuarioDTO.findByCreatedAt", query = "SELECT u FROM UsuarioDTO u WHERE u.createdAt = :createdAt"),

   @NamedQuery(name = "UsuarioDTO.findByLoginSenha", query = "SELECT u FROM UsuarioDTO u WHERE u.login = :login and u.senha = :senha")

})

    public class UsuarioDTO   extends Usuario implements Serializable {

    }
