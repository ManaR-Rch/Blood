package com.bloodbank.dao;

import com.bloodbank.model.Receveur;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReceveurDAO {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public ReceveurDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("blood");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    public void save(Receveur receveur) {
        entityManager.getTransaction().begin();
        entityManager.persist(receveur);
        entityManager.getTransaction().commit();
    }
    
    public Receveur findById(Long id) {
        return entityManager.find(Receveur.class, id);
    }
    
    public List<Receveur> findAll() {
        TypedQuery<Receveur> query = entityManager.createQuery("SELECT r FROM Receveur r", Receveur.class);
        return query.getResultList();
    }
    
    public void update(Receveur receveur) {
        entityManager.getTransaction().begin();
        entityManager.merge(receveur);
        entityManager.getTransaction().commit();
    }
    
    public void delete(Long id) {
        entityManager.getTransaction().begin();
        Receveur receveur = entityManager.find(Receveur.class, id);
        if (receveur != null) {
            entityManager.remove(receveur);
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
