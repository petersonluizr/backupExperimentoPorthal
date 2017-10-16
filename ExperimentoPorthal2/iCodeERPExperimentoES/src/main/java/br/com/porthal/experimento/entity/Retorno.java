/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author douglas
 */
@XmlRootElement
public class Retorno {
    private boolean sucesso;
    private String descricao;

    /**
     * @return the sucesso
     */
    public boolean isSucesso() {
        return sucesso;
    }

    /**
     * @param sucesso the sucesso to set
     */
    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
