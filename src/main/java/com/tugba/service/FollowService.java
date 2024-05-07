package com.tugba.service;

import com.tugba.entity.Follow;
import com.tugba.entity.User;
import com.tugba.repository.FollowRepository;
import com.tugba.repository.UserRepository;
import com.tugba.utility.Response;
import com.tugba.utility.StaticValues;

import java.util.List;
import java.util.Optional;

public class FollowService {
    private final UserService userService;
    private final FollowRepository followRepository;


    public FollowService() {
        this.userService= new UserService();
        this.followRepository= new FollowRepository();
    }

    public Response<Boolean> follower(String username) {
        Response<Boolean> response= new Response();
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()){
          // takip eden kişi static user
            // takip edilen kişiyi bulmak Optional username isEmpty ile sorgula
            if (!followRepository.followIsExist(user.get().getId())){
                followRepository.save(Follow.builder()
                                .followerid(StaticValues.user.getId())
                                .date(System.currentTimeMillis())
                                .followingid(user.get().getId())
                        .build());
                response.setData(true);
                response.setMessage(username + " Adlı Kullanıcı Takip Edildi." );

                response.setStatusCode(200);
                return response;
            } else {
                response.setMessage(username + " Adlı Kullanıcı Zaten Takip Ediliyor!");
                response.setStatusCode(400);

                response.setData(false);
                return response;
            }
        } else {
            response.setData(false);
            response.setMessage(username + " Adlı Kullanıcı Bulunamadı.");
            response.setStatusCode(400);
            return response;
        }
    }

    public List<Follow> findAll() {
      return  followRepository.findAll();
    }
}
