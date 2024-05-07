package com.tugba;

import com.tugba.entity.User;
import com.tugba.utility.enums.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Runner {
    public static void main(String[] args) {



    User user = User.builder()
            .username("Hira")
            .password("H88")
            .email("hirator@gmail.com")
            .isActive(true)
            .phone("555 555 55 58")
            .state(State.BLOKED)
            .build();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Instagram");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        /**
         * persist -> ekleme ve güncelleme
         * id null ise ekleme işlemi yapar.
         * id null değil ise var olan kayıt güncellenir, ancak ilgili id daha önce kullanılmamış ise ekleme yapar.
         *
         *
         * remove (Entity) -> silme işlemi yapar.
         */
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
