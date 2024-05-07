package com.tugba.utility;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Getter
public class Repository<T, ID> implements ICrud<T, ID>{
    private final EntityManagerFactory emf;
    private EntityManager em;
    private T t;
    public Repository(T entity){
        emf= Persistence.createEntityManagerFactory("Instagram");
        em = emf.createEntityManager();
        this.t=entity;
    }

    public void openEntityManager(){
        em =emf.createEntityManager();
    }

    private void openSession(){
        em= emf.createEntityManager();
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
    @Override
    public T save(T entity) {
        openSession();
        em.persist(entity);
        closeSession();
        return entity;
    }



    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        try {
            openSession();
            entities.forEach(em::persist);
            closeSession();
        }catch (Exception e){
            rollBack();
        }
        return entities;
    }

    /**
     * select * from tbl... where id= ?
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        CriteriaBuilder criteriaBuilder= em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery= (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); // select * from
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"),id)); // where id= ?
        T result;
        try {
            /**
             * Burada sorgumuz zaten tek bir sonuç dönecek ya da hiç dönmeyeceği için bulduğu il sonucu vermesini istiyoruz.
             */
            result = em.createQuery(criteriaQuery).getSingleResult();
            return Optional.of(result);
        }catch (NoResultException e){
            System.out.println("Beklenmedik bir hata oluştu." + e.getMessage());
            return Optional.empty();
        }
    }
    private void openSS() {
        if (!em.isOpen())
            em = emf.createEntityManager();
    }
    @Override
    public boolean existById(ID id) {
        CriteriaBuilder criteriaBuilder= em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery= (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); // select * from
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"),id)); // where id= ?
        T result;
        try {

            em.createQuery(criteriaQuery).getSingleResult();
            return true;
        }catch (NoResultException e){
            System.out.println("Beklenmedik bir hata oluştu." + e.getMessage());
            return false;
        }
    }

    @Override
    public List<T> findAll() {
        openSS();
        CriteriaBuilder criteriaBuilder= em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery= (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); // select * from
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<T> findAllByColumnAndValue(String columnName, Object value) {
        CriteriaBuilder criteriaBuilder= em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery= (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); // select * from
        criteriaQuery.where(criteriaBuilder.equal(root.get(columnName),value));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Java Reflection
     * Long userid -> if (userid != null) sorguya dahil et -> userid,value
     *
     * @param entity
     * @return
     */
    @Override
    public List<T> findAllByEntity(T entity) {
        List<T> result;
        Class<?> clazz= entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        CriteriaBuilder criteriaBuilder= em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery= (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); // select * from

        /**
         * Where
         * içeriği null olmayan değişkenlerin where içerisine predicate olarak eklenmesini sağlamak
         */
        List<Predicate> predicateList = new ArrayList<>();
        for (int i = 1; i < fields.length ; i++) { // 1'den başlamasının nedni entity içerisinden aldığımız alanlarda
            // ilk alan sıfır değerine sahip olan Long id bu nedenle diğerlerinden başlıyoruz.
            /**
             * DİKKAT! Bir field erişim belirteçleri ile erişime kapalı olabilir.
             * Bu nedenle bu fieldları private'den public'e çekilmesi gerekir.
             */
            fields[i].setAccessible(true); // Erişime açıyoruz Yani private ---> public
            try {
                /**
                 * Erişime açtığımız fieldların adlarını ve değerlerini okuyoruz.
                 */
                String column = fields[i].getName(); // Kolon isimleri String'dir.
                Object value = fields[i].get(entity); // Bu fieldların içerisindeki değerler String,int vb olduğu için Object kullanılmalıdır.
                if (value!= null){
                    if (value instanceof String){ // Object value String bir ifade ise like kullanılacak
                        predicateList.add(criteriaBuilder.like(root.get(column),"%"+value+"%"));
                    }else { //String olmayan ifadeler equals kullanılarak sorgu yapılır.
                        predicateList.add(criteriaBuilder.equal(root.get(column),value));
                    }
                }
            }catch (Exception e){
                System.out.println("Hata Oluştu." +e.getMessage());
            }
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[]{}));
        result = em.createQuery(criteriaQuery).getResultList();
        return result;
    }

    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(removeElement ->{
            try {
                openSession();
                em.remove(removeElement);
                closeSession();
            }catch (Exception e){
                if (em.isOpen()){
                    rollBack();
                }
            }
        });
    }
}
