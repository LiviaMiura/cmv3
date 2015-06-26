/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapeamento da Superclass e métodos get/set referentes a tabela resposta.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resposta", nullable = false)
    private Integer idResposta;
    @Basic(optional = false)
    @Column(name = "mensagem", nullable = false)
    private int mensagem;
    @Basic(optional = false)
    @Column(name = "amostras", nullable = false)
    private int amostras;
    @Column(name = "datacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacao;

    /*
     * RETIRADO COMENTARIO 0904
     */

    /*
     * inicio das modificacoes
     * 02/04/2011
     */
    //###################################################################################
    @JoinColumn(name = "requisicao_id", referencedColumnName = "id_requisicao", nullable = false)
    @ManyToOne(optional = false)
    private RequisicaoDTO requisicaoId;

    public RequisicaoDTO getRequisicaoId() {
        if (requisicaoId == null) {
            requisicaoId = new RequisicaoDTO();
        }
        return requisicaoId;
    }

    public void setRequisicaoId(RequisicaoDTO requisicaoId) {
        this.requisicaoId = requisicaoId;
    }
   //###################################################################################
    @JoinColumn(name = "resultado_id", referencedColumnName = "id_resultado", nullable = false)
    @ManyToOne(optional = false)
    private ResultadoDTO resultadoId;

    public ResultadoDTO getResultadoId() {
        if (resultadoId == null){
            resultadoId = new ResultadoDTO();
        }
        return resultadoId;
    }

    public void setResultadoId(ResultadoDTO resultadoId) {
        this.resultadoId = resultadoId;
    }


    //###################################################################################
    @JoinColumn(name = "statussistema_id", referencedColumnName = "id_statussistema", nullable = false)
    @ManyToOne(optional = false)
    private StatussistemaDTO statussistemaId;

    public StatussistemaDTO getStatussistemaId() {
        if(statussistemaId == null){
        statussistemaId = new StatussistemaDTO();
        }
        return statussistemaId;
    }

    public void setStatussistemaId(StatussistemaDTO statussistemaId) {
        this.statussistemaId = statussistemaId;
    }

     //###################################################################################

    @JoinColumn(name = "statushw_id", referencedColumnName = "id_statushw", nullable = false)
    @ManyToOne(optional = false)
    private StatushwDTO statushwId;

    public StatushwDTO getStatushwId() {
        if(statushwId == null){
        statushwId = new StatushwDTO();
        }
        return statushwId;
    }

    public void setStatushwId(StatushwDTO statushwId) {
        this.statushwId = statushwId;
    }

  //###################################################################################

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "respostaId")
    private Collection<MedidaDTO> medidaCollection;

    public Collection<MedidaDTO> getMedidaCollection() {
        return medidaCollection;
    }

    public void setMedidaCollection(Collection<MedidaDTO> medidaCollection) {
        this.medidaCollection = medidaCollection;
    }


   //###################################################################################



    public Resposta() {
    }

    public Resposta(Integer idResposta) {
        this.idResposta = idResposta;
    }

    public Resposta(Integer idResposta, int mensagem, int amostras) {
        this.idResposta = idResposta;
        this.mensagem = mensagem;
        this.amostras = amostras;
    }

    public Integer getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Integer idResposta) {
        this.idResposta = idResposta;
    }

    public int getMensagem() {
        return mensagem;
    }

    public void setMensagem(int mensagem) {
        this.mensagem = mensagem;
    }

    public int getAmostras() {
        return amostras;
    }

    public void setAmostras(int amostras) {
        this.amostras = amostras;
    }

    public Date getDatacao() {
        return datacao;
    }

    public void setDatacao(Date datacao) {
        this.datacao = datacao;
    }

   
    

    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResposta != null ? idResposta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resposta)) {
            return false;
        }
        Resposta other = (Resposta) object;
        if ((this.idResposta == null && other.idResposta != null) || (this.idResposta != null && !this.idResposta.equals(other.idResposta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Resposta[idResposta=" + idResposta + "]";
    }
}
