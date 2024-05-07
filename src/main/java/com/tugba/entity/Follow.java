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
@Table(name = "tblfollow")
@NamedQueries({
        @NamedQuery(name = "Follow.isExist", query = "select count(f)>0 from Follow f where f.followingid=:userid")
}

)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long followerid;
    Long followingid;
    Long date;
    /**
     * Kullanıcının profilinin gizli olup olmadığıla ilgili
     */
    Integer state;
}
