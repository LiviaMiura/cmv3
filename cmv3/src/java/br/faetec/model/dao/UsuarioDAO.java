/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.model.dao;

import br.faetec.model.dto.UsuarioDTO;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela usuario no banco de dados.
@author Antonio Cassiano
 **/
public class UsuarioDAO {

    /**
     * Recupera todos os registros da tabela usuário.
    @author Antonio Cassiano
    @return List - Lista preenchida com usuários.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("UsuarioDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela usuário, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  usuarioDTO
    @return UsuarioDTO - preenchido com o registro selecionado.
     **/
    public UsuarioDTO selecionar(UsuarioDTO usuarioDTO) {
        return (UsuarioDTO) Database.manager.find(UsuarioDTO.class, usuarioDTO.getIdUsuario());
    }

    /**
     * Grava um registro na tabela usuário se IdUsuario igual a zero, caso contrario atualiza o registro.
     * Verifica se chave unica do campo login e valido.
    @author Antonio Cassiano
    @param  usuarioDTO
    @return string retorno para navegacao. Retorna string listar para gravacao ou alteracao permitida,caso contrario null
     **/
    public String gravar(UsuarioDTO usuarioDTO) {

        String retorno = "listar";
        Database.manager.getTransaction().begin();
        if (usuarioDTO.getIdUsuario() == 0) {
            Database.manager.persist(usuarioDTO); // gravar
        } else {
            Database.manager.merge(usuarioDTO); // atualizar
        }
        try {
            Database.manager.getTransaction().commit();
            retorno = "listar";
        } catch (Exception e) {
            System.out.println("\n Teste login repetido  ##########" + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login já cadastrado!",
                    "Login já cadastrado!"));
            retorno = null;
        }
        return retorno;
    }

    /**
     * Exclui um registro da tabela usuário, com base no valor da chave primária.
    @author Antonio Cassiano
    @param  usuarioDTO
     **/
    public void excluir(UsuarioDTO usuarioDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(UsuarioDTO.class, usuarioDTO.getIdUsuario()));
        Database.manager.getTransaction().commit();
    }

    /**
     * Este método localiza um usuário pelo login/senha
    @author Antonio Cassiano
    @param  usuarioDTO
    @return usuarioDTO ou erro caso não encontre
    @throws NoResultException
     **/
    public UsuarioDTO validarLogin(UsuarioDTO usuarioDTO) throws NoResultException {
        Object object = Database.manager.createNamedQuery("UsuarioDTO.findByLoginSenha").setParameter("login", usuarioDTO.getLogin()).setParameter("senha", usuarioDTO.getSenha()).getSingleResult();
        usuarioDTO = (UsuarioDTO) object;
        return usuarioDTO;
    }
}
