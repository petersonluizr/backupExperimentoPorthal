/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.entity;

import br.com.porthal.experimento.persistence.MyInterfaceEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ecar. S. M.
 */
@Entity
@Table(name = "NOTA_FISCAL")
public class NotaFiscal implements Serializable, MyInterfaceEntity {

    @Id
    @Column(name = "ID")
    protected Integer id;

    @Column(name = "NUMERO")
    @NotNull(message = "Operação obrigatório")
    protected Long numero;

    @Column(name = "DATA_EMISSAO")
    @NotNull(message = "Operação obrigatório")
    @Temporal(TemporalType.DATE)
    protected Date dataEmissao;

    @Column(name = "TOTAL_PRODUTOS")
    @NotNull(message = "Operação obrigatório")
    protected BigDecimal totalProdutos;

    @Column(name = "TOTAL_NOTA")
    @NotNull(message = "Operação obrigatório")
    protected BigDecimal totalNota;

    @Column(name = "TOTAL_FRETE")
    @NotNull(message = "Operação obrigatório")
    protected BigDecimal totalFrete;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENTE", referencedColumnName = "ID")
    protected Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    protected List<Produto> produtos;

    @ManyToOne
    protected PlanoConta planoConta;

    public Integer getQuantidadeProdutos() {
        return getProdutos().size();
    }

    public NotaFiscal(Integer id, Long numero, Date dataEmissao, BigDecimal totalProdutos, BigDecimal totalNota, BigDecimal totalFrete, Cliente cliente, List<Produto> produtos, PlanoConta planoConta) {
        this.id = id;
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.totalProdutos = totalProdutos;
        this.totalNota = totalNota;
        this.totalFrete = totalFrete;
        this.cliente = cliente;
        this.produtos = produtos;
        this.planoConta = planoConta;
    }

    public NotaFiscal() {
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
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * @return the dataEmissao
     */
    public Date getDataEmissao() {
        return dataEmissao;
    }

    /**
     * @return the totalProdutos
     */
    public BigDecimal getTotalProdutos() {
        return totalProdutos;
    }

    /**
     * @return the totalNota
     */
    public BigDecimal getTotalNota() {
        return totalNota;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @return the produtos
     */
    public List<Produto> getProdutos() {
        return produtos;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * @param dataEmissao the dataEmissao to set
     */
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    /**
     * @param totalProdutos the totalProdutos to set
     */
    public void setTotalProdutos(BigDecimal totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    /**
     * @param totalNota the totalNota to set
     */
    public void setTotalNota(BigDecimal totalNota) {
        this.totalNota = totalNota;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @param produtos the produtos to set
     */
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * @return the totalFrete
     */
    public BigDecimal getTotalFrete() {
        return totalFrete;
    }

    /**
     * @param totalFrete the totalFrete to set
     */
    public void setTotalFrete(BigDecimal totalFrete) {
        this.totalFrete = totalFrete;
    }

    /**
     * @return the planoConta
     */
    public PlanoConta getPlanoConta() {
        return planoConta;
    }

    /**
     * @param planoConta the planoConta to set
     */
    public void setPlanoConta(PlanoConta planoConta) {
        this.planoConta = planoConta;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.numero);
        hash = 53 * hash + Objects.hashCode(this.dataEmissao);
        hash = 53 * hash + Objects.hashCode(this.totalProdutos);
        hash = 53 * hash + Objects.hashCode(this.totalNota);
        hash = 53 * hash + Objects.hashCode(this.totalFrete);
        hash = 53 * hash + Objects.hashCode(this.cliente);
        hash = 53 * hash + Objects.hashCode(this.produtos);
        hash = 53 * hash + Objects.hashCode(this.planoConta);
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
        final NotaFiscal other = (NotaFiscal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.dataEmissao, other.dataEmissao)) {
            return false;
        }
        if (!Objects.equals(this.totalProdutos, other.totalProdutos)) {
            return false;
        }
        if (!Objects.equals(this.totalNota, other.totalNota)) {
            return false;
        }
        if (!Objects.equals(this.totalFrete, other.totalFrete)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.produtos, other.produtos)) {
            return false;
        }
        if (!Objects.equals(this.planoConta, other.planoConta)) {
            return false;
        }
        return true;
    }

}
