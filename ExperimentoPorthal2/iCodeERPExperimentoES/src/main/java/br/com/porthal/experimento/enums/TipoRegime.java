/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.enums;

/**
 *
 * @author Ecar. S. M.
 */
public enum TipoRegime {

    _0(0), _1(1), _2(2);

    private final Integer tipo;

    private TipoRegime(Integer nome) {
        this.tipo = nome;
    }

    public Integer getTipo() {
        return tipo;
    }

}
