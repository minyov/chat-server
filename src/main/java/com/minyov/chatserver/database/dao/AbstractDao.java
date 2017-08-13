package com.minyov.chatserver.database.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public abstract class AbstractDao<T> {

    @PersistenceContext
    protected EntityManager em;

    public Session getHibernateSession() {
        return ((Session) em.getDelegate());
    }

    public Criteria createCriteria() {
        return getHibernateSession().createCriteria(getEntityClass());
    }

    public abstract Class<T> getEntityClass();

    public List<T> getAll() {
        Criteria criteria = createCriteria();

        return criteria.list();
    }

    @Transactional
    public void persist(T entity) {
        em.persist(entity);

        em.flush();
    }

    @Transactional
    public void merge(T entity) {
        em.merge(entity);

        em.flush();
    }

    @Transactional
    public T findById(Long id) {
        return em.find(getEntityClass(), id);
    }
}