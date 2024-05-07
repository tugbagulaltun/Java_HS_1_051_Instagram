package com.tugba.repository;

import com.tugba.entity.Post;
import com.tugba.utility.ICrud;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class PostRepository{
    private final EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder criteriaBuilder;
    public PostRepository(){
        this.emf= Persistence.createEntityManagerFactory("Instagram");
        this.em= emf.createEntityManager();
        this.criteriaBuilder = em.getCriteriaBuilder();
    }
     private void openSession() {
         this.em = emf.createEntityManager();
         em.getTransaction().begin();
     }

     private void closeSession(){
        em.getTransaction().commit();
        em.close();
     }

     private void rollBack(){
        em.getTransaction().rollback();
        em.close();
     }

     public Post save (Post post){
        openSession();
        em.persist(post);
        closeSession();
        return post;
     }


}
