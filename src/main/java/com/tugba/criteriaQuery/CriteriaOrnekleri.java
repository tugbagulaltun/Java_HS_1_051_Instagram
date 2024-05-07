package com.tugba.criteriaQuery;

import com.tugba.entity.Comment;
import com.tugba.entity.Post;
import com.tugba.view.VwComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import javax.swing.text.Position;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CriteriaOrnekleri {
    private EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder criteriaBuilder;
    public  CriteriaOrnekleri(){
        emf= Persistence.createEntityManagerFactory("Instagram");
        em= emf.createEntityManager();
        criteriaBuilder = em.getCriteriaBuilder();
    }

    /**
     *  1 - Select * from tblcomment
     */
    public List<Comment> findAll(){ // 1
        /**
         * Mutlaka bir entity sınıfı verilir. Burada reflection kullanılarak sınıf analiz edilir.
         */
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        /**
         * Select işlemi için seçilecek alanları belirleyebilmek öenmlidir. Elle yazarken alanları biz belirleriz,
         * ancak bu işlem belirsiz sınıflar üzerinden yapılırken Generic Type oalrak alınır ve Reflection ile çözülür.
         */
        Root<Comment> root = criteriaQuery.from(Comment.class);
        /**
         * select * from
         */
        criteriaQuery.select(root);
        /**
         * oluşturduğumuz sorguyu çalıştırıyoruz.
         */
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * 2- Select * from -> iligi entity içinfrki tüm alanlar gelir.
     * Ancak bazen tek bir alan almak ihtiyacı olacaktur böyle durumlarda
     * select comment from tblcomment
     */
    public List<String> selectOneColumn(){ // 2
        CriteriaQuery<String> criteriaQuery= criteriaBuilder.createQuery(String.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root.get("comment"));
    //      List<String> result =  em.createQuery(criteriaQuery).getResultList();
    //      return result;
        return em.createQuery(criteriaQuery).getResultList();
    }



    /**
     * 3- select comment from tblcomment where postid=?
     */
    public List<String> selectOneColumnByPostId(Long postId){ // 3
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<Comment> root= criteriaQuery.from(Comment.class);

        // select comment
        criteriaQuery.select(root.get("comment"));
        // where postid=?
        criteriaQuery.where(criteriaBuilder.equal(root.get("postid"),postId));
        return em.createQuery(criteriaQuery).getResultList();
    }


    /**
     * 4- seelct * from tblcomment where id=?
     * -> Bur sorgu ya tek bir değer döner ya da boş döner.
     */
    public Optional <Comment> findById(Long id){ // 4
        CriteriaQuery<Comment> criteriaQuery=criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root= criteriaQuery.from(Comment.class);
        // select * from
        criteriaQuery.select(root);
        // where id=?
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"),id));
        Comment result  = em.createQuery(criteriaQuery).getSingleResult();
        if (result!=null){
            return Optional.of(result);
        }else
            return Optional.empty();
        //em.createQuery(criteriaQuery).getSingleResult(); --> Null dönme ihtimali olduğu için bunu kullanamazsın

        /**
         * Eğer bir değerin null dönme ihtimali varsa Optional kullanılır.
         * Kullanılmazsa NullPointerException hatası verir. Uygulama Patlar.
         */
    }

    /**
     * 5- select userid, comment from tblcomment where postid=?
     * Örnek
     * userid  ,  comment
     * -> 5,     "ooo güzelmiş"
     * -> 47,     " bende isterim"
     * -> 16,     " bbeklee"
     * --------------------------------
     * ArrayList<String> X
     * Object [] -> {5, "ooo güzelmiş}
     *
     */
    public List<Object[]> selectManyColumn (Long postId){ // 5
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Comment> root= criteriaQuery.from(Comment.class);
        // select userid, comment from
        /**
         * OPSIYON - 1
         */
        //criteriaQuery.select(criteriaBuilder.array(root.get("userid"), root.get("cooment")));
        /**
         * OPSIYON - 2
         */
        Path<Long>  userid= root.get("userid");
        Path<String>  comment= root.get("comment");
        criteriaQuery.select(criteriaBuilder.array(userid,comment));
        criteriaQuery.where(criteriaBuilder.equal(root.get("postid"),postId));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * 6- select * from tblcomment where posid=? and userid>60 comment like '%ankara%'
     */
    public List<Comment> findAllByPostIdAndUserIdGteAndCommentLike(Long postId, Long userId, String comment){ // 6
        CriteriaQuery<Comment> criteriaQuery= criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root); // select * from

        /**
         * where postid =
         * -> postid= root.get("postid")
         * -> ? = postid
         */
        Predicate predicatePostId= criteriaBuilder.equal(root.get("postid"), postId);
        Predicate predicateuserId= criteriaBuilder.greaterThan(root.get("userid"), userId);
        Predicate predicateCommentLike= criteriaBuilder.like(root.get("comment"), "%"+comment+"%");

        criteriaQuery.where(criteriaBuilder.and(predicatePostId,predicateuserId,predicateCommentLike));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * 7- Native Query - Hibernate üzerinden direkt SQL komutlarını çalıştıravildiğimiz yapıdır.
     */
    public List<Comment> findAllNativeSQL(){ // 7
        List<Comment> result = em.createNativeQuery("select * from tblcomment",Comment.class).getResultList();
        return result;
    }

    /**
     * 8- select comment from tblcomment
     */
    public List<String> getOneColumnNativeSQL(){ // 8
        return em.createNativeQuery("select comment from tblcomment", String.class).getResultList();
    }

    /**
     * 9 - select useid,comment from tblcomment
     */
    public List<VwComment> getViewNativeSQL(){ // 9
        return em.createNativeQuery("select id,userid,comment from tblcomment", VwComment.class).getResultList();
    }

    /**
     * 10 - Post.Java sınıfındaki NamedQuery'i çağırma --> findAll
     */
    public List<Post> findAllNamedQuery(){ // 10
      return  em.createNamedQuery("Post.findAll",Post.class).getResultList();
    }

    /**
     * 11 - Post.Java sınıfındaki NamedQuery'i çağırma --> countAll
     */
    public BigDecimal countPostSize(){ // 11
        return em.createNamedQuery("Post.countAll" ,BigDecimal.class).getSingleResult();
    }

    /**
     * 12 - Post.Java sınfıındaki NamedQuery'i çağırma --> Post.findAllByUserId
     */
    public List<Post> findAllByUserId (Long userid){
        TypedQuery<Post> typedQuery = em.createNamedQuery("Post.findAllByUserId",Post.class);
        typedQuery.setParameter("userid",userid); // Metottaki parametreyi kullanabilmek için
        return typedQuery.getResultList();
    }
}
