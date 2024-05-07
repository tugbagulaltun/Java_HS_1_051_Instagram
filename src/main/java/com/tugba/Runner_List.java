package com.tugba;

import com.tugba.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class Runner_List {
    public static void main(String[] args) {

        /**
         * JDBC -> connection, statement(SQL), resultset
         * HIBETNATE ->
         * 1- connection
         * 2- SQL -> criteria (statement kısmı)
         * 3- result
         */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Instagram");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        /**
         * select * from tbluser
         * 1- Criteria sorgusu oluşturmak için gerekli olan kolon bilgilerini ve tablo bilgisini çekmek için kullanacağımız entity sınıfını belirle.
         */
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class); //Kriter oluşturabilmek ve sorgu yazabilmek için User sınıfındaki değişkenleri kullan
        /**
         * 2- select * ya da select id,ad,adres
         * burada select ile hangi kolonların okunacağini belirtiyoruz.
         */
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        /**
         * 2.1. Eğer sorguda kısıtlamalra gidilmek isteniyor ise burada where kullanarak kısıtlar yazılır.
         * Burada kısıtlar predicate olarak tanımlanabilir ve birden fazla kısıt getirilebilir ya da tek bir kısıt var ise direkt yazılabilir.
         */
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"),"Hanbek"));
        criteriaQuery.where(criteriaBuilder.like(root.get("username"),"%e%"));
        /**
         * 3- ilgili sorgu tanımlandığına göre bu sorguyu kullanarak listeyi çekiyoruz.
         */
        List<User> userList= em.createQuery(criteriaQuery).getResultList();
        em.close();
        emf.close();
        System.out.println("Kullanıcı Listesi");
        System.out.println("-----------------------");
        userList.forEach(System.out::println);






    }
}
