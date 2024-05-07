package com.tugba.entity;

import com.tugba.utility.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NamedQueries({
        @NamedQuery(name = "User.isExist",query = "select count(u)>0 from User u where u.username= :userName"),
        @NamedQuery(name = "User.findByUsernameAndPassword",
                            query = "select u from User u where u.username = :userName and u.password = :password")
})
@Data // get - set - to string
@AllArgsConstructor // parametreli consturctorların tümü
@NoArgsConstructor // default constructor
@Builder //
@Entity
@Table(name = "tbluser")
public class User {
    @Id //Long id nin primarykey olduğunu belirtmek için kullanılır.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id için otomatik artan bir HB sequence oluşturur.
    Long id;

//    @Id
//    /**
//     * Kullanılan DB'de hibernate için bir sequence  oluşturur ve id'nin bu sequence ile çalışması sağlanır.
//     * name -> Hibernate için kullanılan isimdir. GeneretedValue için seçiçi isimdir.
//     */
//   @SequenceGenerator(name = "sq_user_id", sequenceName="sq_user_id" , initialValue = 1000, allocationSize = 20)
//   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_user_id")
//   Long id2;

        @Column(name = "userName", unique = true, nullable = false,length = 64, updatable = false)
    String username;
        @Column(nullable = false,length = 128)
    String password;
        @Column(length = 35)
    String email;
        @Column(length = 15)
    String phone;
    Boolean isActive;
    /**
     * Enumlar şu şekilde çalışırlar
     * String -> Integer
     * Active -> 1
     * Eğer enum bir kullanımı tiği belirtmezseniz Integer olarak DB'ye kaydolur.
     * EnumType.ORDINAL -kullanırsan -> State 1 yazar
     * EnumType.STRING -kullanırsan -> State ACTIVE yazar
     */
        @Enumerated(EnumType.STRING) // Enumun sözel olarak değerini okutur.
    State state;

}
