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
 *NamedQueries referentes a tabela cargo.
@author Antonio Cassiano
 **/
@Entity
@Table(name = "cargo", catalog = "cmv3", schema = "")
@NamedQueries({


    @NamedQuery(name = "CargoDTO.findAll", query = "SELECT c FROM CargoDTO c"),
    @NamedQuery(name = "CargoDTO.findByIdCargo", query = "SELECT c FROM CargoDTO c WHERE c.idCargo = :idCargo"),
    @NamedQuery(name = "CargoDTO.findByDescricao", query = "SELECT c FROM CargoDTO c WHERE c.descricao = :descricao")})

    public class CargoDTO extends Cargo  implements Serializable{
}


