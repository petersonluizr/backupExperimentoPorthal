/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.ejb;

import br.com.porthal.experimento.entity.Cliente;
import br.com.porthal.experimento.persistence.NewPersistence;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author Porthal
 */
@Stateless
@LocalBean
public class ClienteSession extends NewPersistence<Cliente, Integer> {

    //<editor-fold defaultstate="collapsed" desc="INIT">
    @PersistenceContext(unitName = "iCodeERPExperimentoPU", name = "iCodeERPExperimentoPU", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    @PostConstruct
    public void init() {
        this.object = new Cliente();
    }

    @Override
    public Cliente getObject() {
        return this.object;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    //</editor-fold>
    
}
