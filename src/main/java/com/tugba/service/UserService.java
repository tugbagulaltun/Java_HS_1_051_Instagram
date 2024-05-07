package com.tugba.service;

import com.tugba.entity.User;
import com.tugba.repository.UserRepository;
import com.tugba.utility.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(){
        this.userRepository = new UserRepository();
    }
    public Response<Boolean> register(String userName, String email, String password) {
        /**
         * 1- userName, daha önce bir kişi aynı ismi almış ise sorun olmalı
         * 2- email, daha önce bir kişi maili almış ise sorun olmalı
         * 3- tanımsız beklenmeyen bir hata olabilir.
         */
   //     List<User> findUser = userRepository.findAllByColumnAndValue("username",userName);
        Response<Boolean> response = new Response<>();
        if (userRepository.isExist(userName)){ // Kullanıcı adı başka birisi tarafından alınmıştır.
            response.setData(false);
            response.setMessage("Kullanıcı adı başka birisi tarafından alınmıştır. Lütfen yeni bir değer girin.");
            response.setStatusCode(400);
            return response;
        }
        userRepository.save(User.builder()
                .username(userName)
                .email(email)
                .password(password)
                .phone("")
                .isActive(true)
                .build());
        response.setData(true);
        response.setStatusCode(200);
        response.setMessage("Kullanıcı başaralı bir şekilde kaydedildi.");
        return response;
    }


    public Response<User> login(String userName, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(userName,password);
        Response<User> response = new Response<>();
        if (userOptional.isEmpty()){
            response.setStatusCode(400);
            response.setMessage("Kullanıcı adı/şifre hatalıdır. Tekrar Deneyin.");
            response.setData(null);
        }
        response.setStatusCode(200);
        response.setMessage("Ok.");
        response.setData(userOptional.get());
        return response;
    }

    public List<User> findAll() {
       return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username){
      List<User> userList = userRepository.findAllByColumnAndValue("username", username);
      if (userList.isEmpty())
          return Optional.empty();
      return Optional.of(userList.getFirst());
    }

}
