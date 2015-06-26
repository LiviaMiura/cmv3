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
 * Mapeamento da Superclass e métodos get/set referentes a tabela requisicao.
@author Antonio Cassiano
 **/
@MappedSuperclass
public class Requisicao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_requisicao", nullable = false)
    private Integer idRequisicao;
    @Basic(optional = false)
    @Column(name = "grupos", nullable = false)
    private int grupos;
    @Column(name = "datacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacao;

    /*
     * Ajuste dos nomes
     *ATENCAO QUANDO TRABALHAR COM RESPOSTA.....####################################################
     * ################################################################################################
     */
    // adicionei DTO   0904
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requisicaoId")
    private Collection<RespostaDTO> respostaCollection;

    public Collection<RespostaDTO> getRespostaCollection() {
        return respostaCollection;
    }

    public void setRespostaCollection(Collection<RespostaDTO> respostaCollection) {
        this.respostaCollection = respostaCollection;
    }
    /*
     * inicio das modificacoes
     * 02/04/2011
     */
    //###################################################################################
    @JoinColumn(name = "comando_id", referencedColumnName = "id_comando", nullable = false)
    @ManyToOne(optional = false)
    private ComandoDTO comandoId;
    public ComandoDTO getComandoId() {
        if (comandoId == null) {
            comandoId = new ComandoDTO();
        }
        return comandoId;
    }
    public void setComandoId(ComandoDTO comandoId) {
        this.comandoId = comandoId;
    }
    //###################################################################################
    @JoinColumn(name = "satelite_id", referencedColumnName = "id_satelite", nullable = false)
    @ManyToOne(optional = false)
    private SateliteDTO sateliteId;
    public SateliteDTO getSateliteId() {
        if (sateliteId == null) {
            sateliteId = new SateliteDTO();
        }
        return sateliteId;
    }

    public void setSateliteId(SateliteDTO sateliteId) {
        this.sateliteId = sateliteId;
    }
    //###################################################################################
    @JoinColumn(name = "estacao_id", referencedColumnName = "id_estacao", nullable = false)
    @ManyToOne(optional = false)
    private EstacaoDTO estacaoId;

    public EstacaoDTO getEstacaoId() {
        if (estacaoId == null) {
            estacaoId = new EstacaoDTO();
        }
        return estacaoId;
    }

    public void setEstacaoId(EstacaoDTO estacaoId) {
        this.estacaoId = estacaoId;
    }
//###################################################################################
    @JoinColumn(name = "missao_id", referencedColumnName = "id_missao", nullable = false)
    @ManyToOne(optional = false)
    private MissaoDTO missaoId;

    public MissaoDTO getMissaoId() {
        if (missaoId == null) {
            missaoId = new MissaoDTO();
        }
        return missaoId;
    }

    public void setMissaoId(MissaoDTO missaoId) {
        this.missaoId = missaoId;
    }
//###################################################################################
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", nullable = false)
    @ManyToOne(optional = false)
    private UsuarioDTO usuarioId;

    public UsuarioDTO getUsuarioId() {
        if (usuarioId == null) {
            usuarioId = new UsuarioDTO();
        }
        return usuarioId;
    }

    public void setUsuarioId(UsuarioDTO usuarioId) {
        this.usuarioId = usuarioId;
    }
    /*
     * fim das modificacoes
     * 02/04/2011
     * #############################################################################
     */

    public Requisicao() {
    }

    public Requisicao(Integer idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public Requisicao(Integer idRequisicao, int grupos) {
        this.idRequisicao = idRequisicao;
        this.grupos = grupos;
    }

    public Integer getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(Integer idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public int getGrupos() {
        return grupos;
    }

    public void setGrupos(int grupos) {
        this.grupos = grupos;
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
        hash += (idRequisicao != null ? idRequisicao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Requisicao)) {
            return false;
        }
        Requisicao other = (Requisicao) object;
        if ((this.idRequisicao == null && other.idRequisicao != null) || (this.idRequisicao != null && !this.idRequisicao.equals(other.idRequisicao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.faetec.model.dto.Requisicao[idRequisicao=" + idRequisicao + "]";
    }
}
