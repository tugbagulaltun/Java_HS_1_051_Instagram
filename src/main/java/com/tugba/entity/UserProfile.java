package com.tugba.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data // get - set - to string
@AllArgsConstructor // parametreli consturctorların tümü
@NoArgsConstructor // default constructor
@Builder //
@Entity
@Table(name = "tbluserprofile")

public class UserProfile {

    @Id //Long id nin primarykey olduğunu belirtmek için kullanılır.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id için otomatik artan bir HB sequence oluşturur.
    Long id;
    Long userid;
    String name;
    String surname;
    String address;
    String image;
    String avatar;
    String about;
    String website;
    Boolean ishidden;

}
