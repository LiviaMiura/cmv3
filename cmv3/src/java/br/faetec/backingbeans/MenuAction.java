/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Métodos para definição das  strings para navegação.
@author Antonio Cassiano
 **/
public class MenuAction {

    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();

    /** Creates a new instance of MenuAction */


    public MenuAction() {
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String administracao.
     **/
    public String administracao() {
        return "administracao";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String usuarioListar.
     **/
    public String usuarioListar() {
        return "usuarioListar";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String cargoListar
     **/
    public String cargoListar() {
        return "cargoListar";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String registroLoginListar
     **/
    public String loginListar() {
        return "registroLoginListar";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String historicoTransacao
     **/
    public String historicoTransacao() {
        return "historicoTransacao";
    }

    /**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String relatorios
     **/
    public String relatorios() {
        return "relatorios";
    }

    /**
     *Controle de navegação para o menu e finalização da session
    @author Antonio Cassiano
    @return String logOff
     **/
    public String logOff() {
        //15.04.2011  finalizar a sessão
        session.invalidate();

        return "logOff";
    }
/**
     *Controle de navegação para o menu
    @author Antonio Cassiano
    @return String menu.
     **/
    public String menu() {
        return "menu";
    }
}
