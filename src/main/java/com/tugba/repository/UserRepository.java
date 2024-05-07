package com.tugba.repository;

import com.tugba.entity.User;
import com.tugba.utility.Repository;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class UserRepository extends Repository<User,Long> {
    public UserRepository(){
        super(new User());
    }

    public boolean isUser(String userName){
        boolean result = (boolean)getEm().createNamedQuery("select count(*)>0 from tbluser where username ='"+userName+"'").getSingleResult();
        return result;
    }

    public boolean isExist(String userName){
        openEntityManager();
        TypedQuery<Boolean> typedQuery = getEm().createNamedQuery("User.isExist", Boolean.class);
        typedQuery.setParameter("userName",userName);
        return typedQuery.getSingleResult();
    }

    public Optional<User> findByUsernameAndPassword(String userName, String password){
        openEntityManager();
        TypedQuery<User> typedQuery=  getEm().createNamedQuery("User.findByUsernameAndPassword",User.class);
        typedQuery.setParameter("userName",userName);
        typedQuery.setParameter("password",password);
        return Optional.ofNullable(typedQuery.getSingleResult());

    }



}
