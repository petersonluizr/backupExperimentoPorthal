package br.com.porthal.experimento.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 */
public abstract class NewPersistence<T extends MyInterfaceEntity, PK extends Integer> {

//    @PersistenceContext
//    private EntityManager entityManager;
    protected T object;

    public abstract void init();

    public Class getObjectClass() {
        return getObject().getClass();
    }

    public T findByCriteria(String column, Object value) {
        Session session = (Session) getEntityManager().getDelegate();
        try {
            return (T) session.createCriteria(this.getObjectClass())
                    .add(Restrictions.eq(column, value))
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List listByCriteria(String column, Object value, Class myClass, String order) {
        HashMap<String, Object> parametros = new HashMap();
        parametros.put(column, value);

        Criteria criteria = getCriteria(myClass);
        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();
            criteria.add(Restrictions.eq(campo, valor));
        }
        return criteria.addOrder(Order.asc(order)).list();
    }

    public Criteria getCriteria(Class classe) {
        Session session = (Session) this.getEntityManager().getDelegate();
        Object objeto = null;
        Transaction tx = null;
        Criteria criteria = null;
        try {
            tx = session.getTransaction();//cria uma transação para o hibernate conectar no banco
            criteria = session.createCriteria(classe);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return criteria;
    }

    public List getObjects(Criteria criteria) {
        Session session = (Session) this.getEntityManager().getDelegate();
        List objects = (List) criteria.list();
//        session.close();
        return objects;
    }

    public Object getObject(HashMap<String, Object> filtros, Class classe) {
        Object objeto = null;
        Criteria criteria = getCriteria(classe);
        for (Map.Entry<String, Object> entry : filtros.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();
            criteria.add(Restrictions.eq(campo, valor));
        }
        criteria.setMaxResults(1);//no maximo 1 resultado
        return getObject(criteria);
    }

    public Object getObject(Criteria criteria) {
        Session session = (Session) this.getEntityManager().getDelegate();
        Object object = criteria.uniqueResult();
        //session.close();
        return object;
    }

    public List getObjects(HashMap<String, Object> filtros, Class classe) {
        Object objeto = null;
        Criteria criteria = getCriteria(classe);
        for (Map.Entry<String, Object> entry : filtros.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();
            criteria.add(Restrictions.eq(campo, valor));
        }
        return getObjects(criteria);
    }

    public MyInterfaceEntity getByCriteria(String column, Object value, Class myClass) {
        Session session = (Session) this.getEntityManager().getDelegate();
        try {
            if (myClass != null) {
                MyInterfaceEntity obj = (MyInterfaceEntity) session.createCriteria(myClass).add(Restrictions.eq(column, value)).uniqueResult();
                if (obj == null) {
                    return null;
                } else {
                    return obj;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public T save(T myEntity) {
        this.object = myEntity;
        if (this.getObject().getId() != null) {
            T entity = getEntityManager().merge(this.getObject());
            getEntityManager().flush();
            init();
            return entity;
        } else {
            getEntityManager().persist(this.getObject());
            T entity = (getById((PK) this.getObject().getId()));
            init();
            return entity;
        }
    }

    public boolean remove(T myEntity) {
        this.object = myEntity;
        getEntityManager().remove(getEntityManager().merge(this.getObject()));
        if (getById((PK) this.getObject().getId()) == null) {
            init();
            return true;
        } else {
            init();
            return false;
        }
    }

    public T getById(Integer id) {
        return (T) getEntityManager().find(this.getObjectClass(), id);
    }

    public T getById(Integer id, Class classe) {
        return (T) getEntityManager().find(classe, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(this.getObjectClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List findAll(Class classe) {
        Object objeto = null;
        Criteria criteria = getCriteria(classe);
        return getObjects(criteria);
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(this.getObjectClass()));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    //<editor-fold defaultstate="collapsed" desc="GET/SET">
    public abstract EntityManager getEntityManager();

    public abstract T getObject();

    public void setObject(T object) {
        this.object = object;
    }

    @Transactional()
    public void bulkSave(Collection<T> entities) {
        EntityManager em = getEntityManager();
        int i = 0;
        int batchSize = 50;
        for (T t : entities) {
            em.merge(t);
            System.out.println(i);
            i++;
            if (i % batchSize == 0) {
                // Flush a batch of inserts and release memory.
                em.flush();
                em.clear();

            }
        }
    }

}
//</editor-fold>
