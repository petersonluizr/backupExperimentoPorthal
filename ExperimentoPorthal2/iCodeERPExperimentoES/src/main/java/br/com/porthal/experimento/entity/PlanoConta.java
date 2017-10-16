/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.entity;

import br.com.porthal.experimento.enums.TipoConta;
import br.com.porthal.experimento.enums.TipoRegime;
import br.com.porthal.experimento.persistence.MyInterfaceEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ecar. S. M.
 */
@Entity
@Table(name = "PLANO_CONTA")
public class PlanoConta implements Serializable, MyInterfaceEntity {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TIPO_CONTA", columnDefinition = "char(1)")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Operação obrigatório")
    private TipoConta tipoConta;

    @Column(name = "CLASSIFICACAO")
    @Size(min = 1, max = 6)
    @NotNull(message = "Operação obrigatório")
    private String classificação;

    @Column(name = "TIPO_OPERACAO", columnDefinition = "char(1)")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "Operação obrigatório")
    private TipoRegime tipoRegime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENTE", referencedColumnName = "ID")
    @NotNull(message = "Operação obrigatório")
    private Cliente cliente;

    @OneToMany(mappedBy = "planoConta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<NotaFiscal> notasFiscais;

    //<editor-fold defaultstate="collapsed" desc="GET / SET">
    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the tipoConta
     */
    public TipoConta getTipoConta() {
        return tipoConta;
    }

    /**
     * @return the classificação
     */
    public String getClassificação() {
        return classificação;
    }

    /**
     * @return the tipoRegime
     */
    public TipoRegime getTipoRegime() {
        return tipoRegime;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @return the notasFiscais
     */
    public List<NotaFiscal> getNotasFiscais() {
        return notasFiscais;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param tipoConta the tipoConta to set
     */
    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    /**
     * @param classificação the classificação to set
     */
    public void setClassificação(String classificação) {
        this.classificação = classificação;
    }

    /**
     * @param tipoRegime the tipoRegime to set
     */
    public void setTipoRegime(TipoRegime tipoRegime) {
        this.tipoRegime = tipoRegime;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @param notasFiscais the notasFiscais to set
     */
    public void setNotasFiscais(List<NotaFiscal> notasFiscais) {
        this.notasFiscais = notasFiscais;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.tipoConta);
        hash = 47 * hash + Objects.hashCode(this.classificação);
        hash = 47 * hash + Objects.hashCode(this.tipoRegime);
        hash = 47 * hash + Objects.hashCode(this.cliente);
        hash = 47 * hash + Objects.hashCode(this.notasFiscais);
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
        final PlanoConta other = (PlanoConta) obj;
        if (!Objects.equals(this.classificação, other.classificação)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.tipoConta != other.tipoConta) {
            return false;
        }
        if (this.tipoRegime != other.tipoRegime) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.notasFiscais, other.notasFiscais)) {
            return false;
        }
        return true;
    }

}
