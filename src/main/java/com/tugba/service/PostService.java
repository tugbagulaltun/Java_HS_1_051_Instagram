package com.tugba.service;

import com.tugba.entity.Post;
import com.tugba.entity.User;
import com.tugba.repository.PostRepository;
import com.tugba.repository.UserRepository;

import java.util.Optional;

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService(){
        this.postRepository= new PostRepository();
        this.userRepository = new UserRepository();
    }

    public void addNewPost(Long userId, String comment, String imageUrl, String location ){
        Optional<User> user = userRepository.findById(userId); // User var mı diye kontrol edilmelidir.
        if (user.isEmpty()) return; // Eğer user yok ise işlemi bitir.
        postRepository.save(Post.builder()
                        .comment(comment)
                        .commentcount(0)
                        .imageurl(imageUrl)
                        .likes(0)
                        .location(location)
                        .shareddate(System.currentTimeMillis())
                        .userid(userId)
                .build());

    }
}
