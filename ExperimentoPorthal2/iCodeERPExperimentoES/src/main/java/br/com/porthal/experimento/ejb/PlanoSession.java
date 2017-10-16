/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.ejb;

import br.com.porthal.experimento.entity.NotaFiscal;
import br.com.porthal.experimento.entity.PlanoConta;
import br.com.porthal.experimento.entity.Produto;
import br.com.porthal.experimento.persistence.NewPersistence;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;

/**
 *
 * @author Ecar. S. M.
 */
@Stateless
@LocalBean
public class PlanoSession extends NewPersistence<PlanoConta, Integer> {

    //<editor-fold defaultstate="collapsed" desc="INIT">
    @PersistenceContext(unitName = "iCodeERPExperimentoPU", name = "iCodeERPExperimentoPU", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    @PostConstruct
    public void init() {
        this.object = new PlanoConta();
    }

    @Override
    public PlanoConta getObject() {
        return this.object;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    //</editor-fold>

    public List<NotaFiscal> consultarNotasFiscais(int idPlanoContas) {
        List<NotaFiscal> listaNotas = null;
        PlanoConta contaEncontrada = null;

        try {
            contaEncontrada = getById(idPlanoContas);
            if (contaEncontrada != null) {
                listaNotas = listByCriteria("planoConta", contaEncontrada, NotaFiscal.class, "id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return listaNotas;
    }

    public List<NotaFiscal> consultarPorCliente(int idCliente) {
        List<NotaFiscal> listaNotas = null;
        List<PlanoConta> planoContas = null;

        try {
            planoContas = listByCriteria("cliente", idCliente, PlanoConta.class, "id");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        if (planoContas != null) {
            for (PlanoConta planoConta : planoContas) {
                listaNotas.addAll(planoConta.getNotasFiscais());
            }

        }

        return listaNotas;
    }

    public BigDecimal somarNotas(List<NotaFiscal> notaFiscais) {
        BigDecimal total = BigDecimal.ZERO;
        for (NotaFiscal notaFiscal : notaFiscais) {
            total.add(notaFiscal.getTotalNota());
        }
        return total;
    }

}
