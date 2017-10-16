/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.entity;

import br.com.porthal.experimento.persistence.MyInterfaceEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ecar. S. M.
 */
@Entity
@Table(name = "PRODUTO")
public class Produto implements Serializable, MyInterfaceEntity {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CODIGO_BARRAS")
    @NotNull(message = "Operação obrigatório")
    private String codigoBarras;

    @Column(name = "COR_DIMENSOES")
    @NotNull(message = "Operação obrigatório")
    private String corDimensoes;

    @Column(name = "DESCRICAO")
    @NotNull(message = "Operação obrigatório")
    private String descricao;

    @Column(name = "QUANTIDADE")
    @NotNull(message = "Operação obrigatório")
    private Integer quantidade;

    @Column(name = "VALOR_UNITARIO")
    @NotNull(message = "Operação obrigatório")
    private BigDecimal valorUnitario;

    @Column(name = "VALOR_DESCONTO")
    @NotNull(message = "Operação obrigatório")
    private BigDecimal valorDesconto;

    @Column(name = "VALOR_TOTAL_FRETE")
    @NotNull(message = "Operação obrigatório")
    private BigDecimal valorTotalFrete;

    @Column(name = "VALOR_TOTAL")
    @NotNull(message = "Operação obrigatório")
    private BigDecimal valorTotal;

    public BigDecimal getTotalUnitario() {
        return getValorUnitario().multiply(BigDecimal.valueOf(getQuantidade())).subtract(getValorDesconto());
    }

    //<editor-fold defaultstate="collapsed" desc="GET / SET">
    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the codigoBarras
     */
    public String getCodigoBarras() {
        return codigoBarras;
    }

    /**
     * @return the corDimensoes
     */
    public String getCorDimensoes() {
        return corDimensoes;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return the quantidade
     */
    public Integer getQuantidade() {
        return quantidade;
    }

    /**
     * @return the valorUnitario
     */
    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    /**
     * @return the valorDesconto
     */
    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    /**
     * @return the valorTotalFrete
     */
    public BigDecimal getValorTotalFrete() {
        return valorTotalFrete;
    }

    /**
     * @return the valorTotal
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param codigoBarras the codigoBarras to set
     */
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    /**
     * @param corDimensoes the corDimensoes to set
     */
    public void setCorDimensoes(String corDimensoes) {
        this.corDimensoes = corDimensoes;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @param valorUnitario the valorUnitario to set
     */
    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /**
     * @param valorDesconto the valorDesconto to set
     */
    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    /**
     * @param valorTotalFrete the valorTotalFrete to set
     */
    public void setValorTotalFrete(BigDecimal valorTotalFrete) {
        this.valorTotalFrete = valorTotalFrete;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.codigoBarras);
        hash = 19 * hash + Objects.hashCode(this.corDimensoes);
        hash = 19 * hash + Objects.hashCode(this.descricao);
        hash = 19 * hash + Objects.hashCode(this.quantidade);
        hash = 19 * hash + Objects.hashCode(this.valorUnitario);
        hash = 19 * hash + Objects.hashCode(this.valorDesconto);
        hash = 19 * hash + Objects.hashCode(this.valorTotalFrete);
        hash = 19 * hash + Objects.hashCode(this.valorTotal);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.codigoBarras, other.codigoBarras)) {
            return false;
        }
        if (!Objects.equals(this.corDimensoes, other.corDimensoes)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        if (!Objects.equals(this.valorUnitario, other.valorUnitario)) {
            return false;
        }
        if (!Objects.equals(this.valorDesconto, other.valorDesconto)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalFrete, other.valorTotalFrete)) {
            return false;
        }
        if (!Objects.equals(this.valorTotal, other.valorTotal)) {
            return false;
        }
        return true;
    }

}
