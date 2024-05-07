package com.tugba.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // get - set - to string
@AllArgsConstructor // parametreli consturctorların tümü
@NoArgsConstructor // default constructor
@Builder //
public class Response<T>{
    /**
     * 100 -> Başarılı
     * 400 -> Başarısız - Kullanıcı Kaynaklı
     * 500 -> Başarısız - Program Kaynaklı
     */
    int statusCode;

    /**
     * Başarılı ise olumlu mesajlar
     * Başarısız ise nedeni açıklamalı olaeak belirten mesajlar
     */
    String message;


    /**
     * Eğer başarılı bir şekilde sonlanmış ise kullanıcıya iletilecek bilgi datası.
     */
    T data;
}
