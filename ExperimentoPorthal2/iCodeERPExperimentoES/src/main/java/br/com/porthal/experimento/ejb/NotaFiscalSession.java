/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.ejb;

import br.com.porthal.experimento.entity.Cliente;
import br.com.porthal.experimento.entity.NotaFiscal;
import br.com.porthal.experimento.entity.Produto;
import br.com.porthal.experimento.persistence.NewPersistence;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author Ecar. S. M.
 */
@Stateless
@LocalBean
public class NotaFiscalSession extends NewPersistence<NotaFiscal, Integer> {

    //<editor-fold defaultstate="collapsed" desc="INIT">
    @PersistenceContext(unitName = "iCodeERPExperimentoPU", name = "iCodeERPExperimentoPU", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    @PostConstruct
    public void init() {
        this.object = new NotaFiscal();
    }

    @Override
    public NotaFiscal getObject() {
        return this.object;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    //</editor-fold>

    public void importar(List<NotaFiscal> nfs) {

        for (NotaFiscal notaFiscal : nfs) {
            calculaTotalProdutos(notaFiscal);
            calculaTotalProdutos(notaFiscal);
            calculaTotalFreteProduto(notaFiscal);
            calculaTotalNotaFiscal(notaFiscal);
        }
        this.bulkSave(nfs);
    }

//    private boolean verificaTotalFrete(NotaFiscal notaFiscal) {
//
//        BigDecimal total = BigDecimal.ZERO;
//        for (Produto produto : notaFiscal.getProdutos()) {
//            total.add(produto.getValorTotalFrete());
//        }
//
//        if (notaFiscal.equals(total)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    public NotaFiscal calculaTotalProdutos(NotaFiscal notaFiscal) {

        BigDecimal total = BigDecimal.ZERO;
        for (Produto produto : notaFiscal.getProdutos()) {
            total.add(produto.getTotalUnitario());
        }

        notaFiscal.setTotalProdutos(total.setScale(2, RoundingMode.HALF_EVEN));

        return notaFiscal;
    }

    public NotaFiscal calculaTotalFreteProduto(NotaFiscal notaFiscal) {

        for (Produto produto : notaFiscal.getProdutos()) {
            produto.setValorTotalFrete((notaFiscal.getTotalFrete().divide(notaFiscal.getTotalProdutos()).multiply(produto.getValorTotal())).divide(BigDecimal.valueOf(notaFiscal.getQuantidadeProdutos())).setScale(2, RoundingMode.HALF_EVEN));
        }

        return notaFiscal;
    }
//
//    private NotaFiscal calculaTotalFrete(NotaFiscal notaFiscal) {
//
//        BigDecimal total = BigDecimal.ZERO;
//        for (Produto produto : notaFiscal.getProdutos()) {
//            total.add(produto.getValorTotalFrete());
//        }
//
//        if (notaFiscal.getTotalFrete() != total) {
//            notaFiscal.setTotalFrete(total);
//        }
//
//        return notaFiscal;
//    }

    public NotaFiscal calculaTotalNotaFiscal(NotaFiscal notaFiscal) {

        BigDecimal total = BigDecimal.ZERO;
        for (Produto produto : notaFiscal.getProdutos()) {
            total.add(produto.getValorTotal());
        }

        notaFiscal.setTotalNota(total.setScale(2, RoundingMode.HALF_EVEN));

        return notaFiscal;
    }

    public List<NotaFiscal> consultarPorCliente(Cliente cliente) {
        List<NotaFiscal> listaNotas = new ArrayList<>();
        try {
            listaNotas = listByCriteria("cliente", cliente, NotaFiscal.class, "id");

            System.out.println(listaNotas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return listaNotas;
    }

    public BigDecimal somarNotas(List<NotaFiscal> notaFiscais) {
        BigDecimal total = BigDecimal.ZERO;
        for (NotaFiscal notaFiscal : notaFiscais) {
            total = total.add(notaFiscal.getTotalNota());
        }
        return total;
    }

}
