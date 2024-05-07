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
@Table(name = "tbllike")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long postid;
    Long userid;
    Long date;
    boolean repeated; // false
    boolean islike; // true

    /**
     * repeated = true ve islike = false ise -> önceden like yapmış sonra like kaldırmış.
     * repeated = false ve islike = false ise -> hiç like yaomamış
     */
}
