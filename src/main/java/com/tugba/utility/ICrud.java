package com.tugba.utility;

import com.tugba.entity.Comment;
import com.tugba.entity.Like;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ICrud <T, ID>{
    T save(T entity);
    void deleteById(ID id);
    Iterable<T> saveAll(Iterable<T> entities); //Iterable herhangi bir liste alması için. List,Set kullanılabilir
    Optional<T> findById(ID id); // ID datatype değişebileceği için bunu da Jenerik yapılmalıyız. ve null değer dönebileceği için Optional yapılmalıdır. Null değer görmek istemeyiz empty görmek isityoruz.
    boolean existById(ID id);
    List<T> findAll();
    List<T> findAllByColumnAndValue(String columnName, Object value); //Buradaki value hepsini(String,integer,boolean vs.) kapsadığı içi Object olmalıdır.
    List<T> findAllByEntity(T entity);

}


