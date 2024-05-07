package com.tugba.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.namespace.QName;

/**
 * Named Query
 * Önbellekleme yapabilirler, bu nedenle aynı sorgu tekrar çağrıldığında veriler önbrllekten çekilir.
 * NamedQuery yazabilmek için 3 farklı dil kullanabilirsiniz.
 * HQL -> Hibernate Query Language
 * JPQL -> Jakarta Persistence Query Language
 * NativeSQL
 * ---------------------------------------------
 *      SORGU YAZMA ŞEKİLLERİ
 * NativeSQL -> select * from tblpost
 * JPQL      -> select p rom Post p (Post p -> alians kullanılır. Yani Post kısaca p ile gösterilir)
 * HQL       -> from Post
 * ---------------------------------------------
 * Named Queryler ilgili sınıfın üzeirne bir anatasyon yardımı ile yazılırlar.
 * Eğer tek bir Query kullanılacak ise tek tek yazılabilir, birden fazla sorgu yazılacak ise
 * array şekilinde queryler eklenbilitler
 * Integer[] {12,2113,445,5,999,852}
 */

 @NamedQueries({
         /**
          * NamedQuery'lerde isimlendirme yaparken şu formatta yazmak uygundur. [Entity_Name].[Query_Name]
          * Hangi entity'e ait olduğunu anlamak için formata uymak önemlidir.
          */
         @NamedQuery(name = "Post.findAll", query = "select p from Post p"),
         @NamedQuery(name = "Post.countAll", query = "select count(p) from Post p"),
         /**
          * DİKKAT!! Eğer NamedQury içerisine bir değer girmeniz gerekiyor ise bunu
          * eklemek için değişken tanımlamalısınız. NamedQuery içerisine değişken tanımlamak için ":[DEGISKEN_ADI]" şeklinde kullanmalısınız.
          */
         @NamedQuery(name = "Post.findAllByUserId", query = "select p from Post p where p.userid = :userid")
 })
@Data // get - set - to string
@AllArgsConstructor // parametreli consturctorların tümü
@NoArgsConstructor // default constructor
@Builder //
@Entity
@Table(name = "tblpost")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userid;
    Long shareddate;
    String comment;
    String imageurl;
    String location;
    Integer likes;
    Integer commentcount;

}
