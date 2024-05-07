package com.tugba.repository;

import com.tugba.entity.Follow;
import com.tugba.utility.ICrud;
import com.tugba.utility.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.net.IDN;
import java.util.List;
import java.util.Optional;

public class FollowRepository extends Repository<Follow,Long> {

    private final EntityManagerFactory emf;
    private EntityManager em;
    public FollowRepository(){
        super(new Follow());
        emf= Persistence.createEntityManagerFactory("Instagram");
    }
    private void openSession(){
        em= emf.createEntityManager();
        em.getTransaction().begin();
    }
    private void closeSession(){
        em.getTransaction().commit();
        em.close();
    }
    private  void rollback(){
        em.getTransaction().rollback();
        em.close();
    }




    public boolean followIsExist(Long userid) {
            openEntityManager();
            TypedQuery<Boolean> typedQuery = getEm().createNamedQuery("Follow.isExist", Boolean.class);
            typedQuery.setParameter("userid",userid);
            return typedQuery.getSingleResult();
    }
}
