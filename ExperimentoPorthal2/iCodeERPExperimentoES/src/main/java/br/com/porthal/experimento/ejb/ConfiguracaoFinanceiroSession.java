package br.com.porthal.experimento.ejb;

import br.com.porthal.experimento.entity.ConfiguracaoFinanceiro;
import br.com.porthal.experimento.persistence.NewPersistence;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 */
@Stateless
@LocalBean
public class ConfiguracaoFinanceiroSession extends NewPersistence<ConfiguracaoFinanceiro, Integer> {

    //<editor-fold defaultstate="collapsed" desc="INIT">
    @PersistenceContext(unitName = "iCodeERPExperimentoPU", name = "iCodeERPExperimentoPU", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    @PostConstruct
    public void init() {
        this.object = new ConfiguracaoFinanceiro();
    }

    @Override
    public ConfiguracaoFinanceiro getObject() {
        return this.object;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    //</editor-fold>

    public ConfiguracaoFinanceiro getConfiguracao() {
        ConfiguracaoFinanceiro grupo = null;
        try {
            grupo = (ConfiguracaoFinanceiro) getCriteria(ConfiguracaoFinanceiro.class).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return grupo;
    }

}
