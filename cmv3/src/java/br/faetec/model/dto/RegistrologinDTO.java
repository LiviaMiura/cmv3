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


/**
 *NamedQueries referentes a tabela registrologin.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "registrologin", catalog = "cmv3", schema = "")
@NamedQueries({
//    @NamedQuery(name = "RegistrologinDTO.findAll", query = "SELECT r FROM RegistrologinDTO r"),
    @NamedQuery(name = "RegistrologinDTO.findAll", query = "SELECT r FROM RegistrologinDTO r order by r.idLogin desc"),       
    @NamedQuery(name = "RegistrologinDTO.findByIdLogin", query = "SELECT r FROM RegistrologinDTO r WHERE r.idLogin = :idLogin"),
    @NamedQuery(name = "RegistrologinDTO.findByData", query = "SELECT r FROM RegistrologinDTO r WHERE r.dataCriacao = :data")})
public class RegistrologinDTO extends Registrologin implements Serializable {

        
}
