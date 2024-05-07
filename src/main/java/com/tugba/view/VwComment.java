package com.tugba.view;

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

public class VwComment {
    @Id //Long id nin primarykey olduğunu belirtmek için kullanılır.
    Long id;
    Long userid;
    String comment;
}
