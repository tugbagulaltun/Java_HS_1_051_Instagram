package com.tugba;

import com.tugba.entity.User;
import com.tugba.repository.UserRepository;

public class Runner_RepositoryTest {
    public static void main(String[] args) {
        User user = User.builder().username("t")
                 .build();

        UserRepository userRepository=new UserRepository();
        userRepository.findAllByEntity(user).forEach(System.out::println);
    }
}
