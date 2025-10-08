package com.bloodbank.dao;

import com.bloodbank.model.Donneur;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class DonneurDAO {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public DonneurDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("blood");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    public void save(Donneur donneur) {
        entityManager.getTransaction().begin();
        entityManager.persist(donneur);
        entityManager.getTransaction().commit();
    }
    
    public Donneur findById(Long id) {
        return entityManager.find(Donneur.class, id);
    }
    
    public List<Donneur> findAll() {
        TypedQuery<Donneur> query = entityManager.createQuery("SELECT d FROM Donneur d", Donneur.class);
        return query.getResultList();
    }
    
    public void update(Donneur donneur) {
        entityManager.getTransaction().begin();
        entityManager.merge(donneur);
        entityManager.getTransaction().commit();
    }
    
    public void delete(Long id) {
        entityManager.getTransaction().begin();
        Donneur donneur = entityManager.find(Donneur.class, id);
        if (donneur != null) {
            entityManager.remove(donneur);
        }
        entityManager.getTransaction().commit();
    }
    
    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
