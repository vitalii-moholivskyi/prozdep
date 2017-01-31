package dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Transactional
class Dao<T> {

    @Resource
    protected SessionFactory sessionFactory;
    private Class entityClass;

    protected Dao(Class entityClass){
        this.entityClass = entityClass;
    }

    public void insert(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public void update(T o) {
        sessionFactory.getCurrentSession().merge(o);
    }

    public void remove(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    public T find(int id) {
        return (T) sessionFactory.getCurrentSession().get(entityClass, id);
    }

    public void saveOrUpdate(T o) {
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    public List<T> findAll() {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(entityClass);
        return crit.list();
    }
}