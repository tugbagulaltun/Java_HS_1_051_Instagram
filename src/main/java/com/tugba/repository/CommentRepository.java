package com.tugba.repository;

import com.tugba.entity.Comment;
import com.tugba.utility.ICrud;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class CommentRepository  implements ICrud <Comment, Long> {
    private final EntityManagerFactory emf;
    private EntityManager em;

    public CommentRepository(){ //constructor içine yazıldı. Bu sınıf çağırıldığında direkt erişecek.
        emf = Persistence.createEntityManagerFactory("Instagram");
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

    @Override
    public Comment save(Comment entity) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Instagram");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(entity);
//        em.getTransaction().commit(); //Bu entity içeriisnde id bilgisi yok, işlem commit edildiğinde DB'den id bilgisi entity içerisine eklenir. Ve return kısmında id bilgisini de görüp kullanıcının bilgileri gösterilir.
//        em.close();
//        emf.close();
        openSession();
        em.persist(entity);
        closeSession();
        return entity;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Iterable<Comment> saveAll(Iterable<Comment> entities) {
//        EntityManagerFactory emf= Persistence.createEntityManagerFactory("Instagram");
//        EntityManager em= emf.createEntityManager();
//        em.getTransaction().begin();
//        entities.forEach(entity ->{
//            em.persist(entity);
//        });
//        em.getTransaction().commit();
//        em.close();
//        emf.close();
        try {
            openSession();
            entities.forEach(em::persist);
            closeSession();
        }catch (Exception ex){
            rollback();
        }
        return entities;
    }

    @Override
    public Optional<Comment> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existById(Long aLong) {
        return false;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public List<Comment> findAllByColumnAndValue(String columnName, Object name) {
        return null;
    }

    @Override
    public List<Comment> findAllByEntity(Comment entity) {
        return null;
    }
}
