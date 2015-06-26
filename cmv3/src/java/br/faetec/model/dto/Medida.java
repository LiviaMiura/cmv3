/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faetec.model.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Mapeamento da Superclass e métodos get/set referentes a tabela medida.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Medida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_medida", nullable = false)
    private Integer idMedida;
    @Basic(optional = false)

    @Column(name = "utc", nullable = false, length = 40)
    private String utc;
    @Basic(optional = false)

    @Column(name = "mensagem", nullable = false)
    private int mensagem;
    @Basic(optional = false)



    @Column(name = "totalizador", nullable = false)
    private int totalizador;
    @Column(name = "intervalo")
    private Integer intervalo;
    @Basic(optional = false)
    @Column(name = "frequencia", nullable = false)
    private double frequencia;


    /*
     * 09/04  add DTO    ajuste dos nomes ########################################
     *
     */

    @JoinColumn(name = "resposta_id", referencedColumnName = "id_resposta", nullable = false)
    @ManyToOne(optional = false)
    private RespostaDTO respostaId;

    public RespostaDTO getRespostaId() {
        if (respostaId == null){
        respostaId = new RespostaDTO();
        }
        return respostaId;
    }

    public void setRespostaId(RespostaDTO respostaId) {
        this.respostaId = respostaId;
    }




    public Medida() {
    }

    public Medida(Integer idMedida) {
        this.idMedida = idMedida;
    }

    public Medida(Integer idMedida, int mensagem, int totalizador, double frequencia) {
        this.idMedida = idMedida;
        this.mensagem = mensagem;
        this.totalizador = totalizador;
        this.frequencia = frequencia;
    }

    public Integer getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(Integer idMedida) {
        this.idMedida = idMedida;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }





    public int getMensagem() {
        return mensagem;
    }

    public void setMensagem(int mensagem) {
        this.mensagem = mensagem;
    }

    public int getTotalizador() {
        return totalizador;
    }

    public void setTotalizador(int totalizador) {
        this.totalizador = totalizador;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMedida != null ? idMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medida)) {
            return false;
        }
        Medida other = (Medida) object;
        if ((this.idMedida == null && other.idMedida != null) || (this.idMedida != null && !this.idMedida.equals(other.idMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Medida[idMedida=" + idMedida + "]";
    }

}
