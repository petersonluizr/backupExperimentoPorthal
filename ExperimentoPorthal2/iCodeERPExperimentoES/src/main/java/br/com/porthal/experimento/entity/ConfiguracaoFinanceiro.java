package br.com.porthal.experimento.entity;

import br.com.porthal.experimento.persistence.MyInterfaceEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 */
@Entity
@Table(name = "CONFIGURACAO_FINANCEIRO")
public class ConfiguracaoFinanceiro implements Serializable, MyInterfaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "CALCULAR_JURO")
    private boolean calcularJuro;

    @NotNull
    @Column(name = "TOLERANCIA_ATRASO_SEM_JURO")
    private Integer toleranciaAtrasoSemJuro;

    @NotNull
    @Column(name = "JURO_ATRASO")
    private BigDecimal juroAtraso;

    @NotNull
    @Column(name = "CALCULAR_MULTA")
    private boolean calcularMulta;

    @NotNull
    @Column(name = "TOLERANCIA_ATRASO_SEM_MULTA")
    private Integer toleranciaAtrasoSemMulta;

    @NotNull
    @Column(name = "MULTA_ATRASO")
    private BigDecimal multaAtraso;

    public ConfiguracaoFinanceiro() {
        this.calcularJuro = true;
        this.calcularMulta = true;
        this.toleranciaAtrasoSemJuro = 0;
        this.toleranciaAtrasoSemMulta = 0;
        this.juroAtraso = BigDecimal.ZERO;
        this.multaAtraso = BigDecimal.ZERO;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the toleranciaAtrasoSemJuro
     */
    public Integer getToleranciaAtrasoSemJuro() {
        return toleranciaAtrasoSemJuro;
    }

    /**
     * @param toleranciaAtrasoSemJuro the toleranciaAtrasoSemJuro to set
     */
    public void setToleranciaAtrasoSemJuro(Integer toleranciaAtrasoSemJuro) {
        this.toleranciaAtrasoSemJuro = toleranciaAtrasoSemJuro;
    }

    /**
     * @return the toleranciaAtrasoSemMulta
     */
    public Integer getToleranciaAtrasoSemMulta() {
        return toleranciaAtrasoSemMulta;
    }

    /**
     * @param toleranciaAtrasoSemMulta the toleranciaAtrasoSemMulta to set
     */
    public void setToleranciaAtrasoSemMulta(Integer toleranciaAtrasoSemMulta) {
        this.toleranciaAtrasoSemMulta = toleranciaAtrasoSemMulta;
    }

    /**
     * @return the juroAtraso
     */
    public BigDecimal getJuroAtraso() {
        return juroAtraso;
    }

    /**
     * @param juroAtraso the juroAtraso to set
     */
    public void setJuroAtraso(BigDecimal juroAtraso) {
        this.juroAtraso = juroAtraso;
    }

    /**
     * @return the multaAtraso
     */
    public BigDecimal getMultaAtraso() {
        return multaAtraso;
    }

    /**
     * @param multaAtraso the multaAtraso to set
     */
    public void setMultaAtraso(BigDecimal multaAtraso) {
        this.multaAtraso = multaAtraso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.getId());
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
        final ConfiguracaoFinanceiro other = (ConfiguracaoFinanceiro) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * @return the calcularJuro
     */
    public boolean isCalcularJuro() {
        return calcularJuro;
    }

    /**
     * @param calcularJuro the calcularJuro to set
     */
    public void setCalcularJuro(boolean calcularJuro) {
        this.calcularJuro = calcularJuro;
    }

    /**
     * @return the calcularMulta
     */
    public boolean isCalcularMulta() {
        return calcularMulta;
    }

    /**
     * @param calcularMulta the calcularMulta to set
     */
    public void setCalcularMulta(boolean calcularMulta) {
        this.calcularMulta = calcularMulta;
    }

}
