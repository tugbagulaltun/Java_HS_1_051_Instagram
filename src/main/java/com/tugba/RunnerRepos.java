package com.tugba;

import com.tugba.entity.Comment;
import com.tugba.entity.Like;
import com.tugba.repository.CommentRepository;
import com.tugba.repository.LikeRepository;

public class RunnerRepos {
    public static void main(String[] args) {

        CommentRepository commentRepository = new CommentRepository();
        commentRepository.save(Comment.builder()
                        .comment("Yeni bir yorum yaptım")
                        .date(System.currentTimeMillis())
                        .postid(1L)
                        .userid(2L)
                .build());

//        LikeRepository likeRepository = new LikeRepository();
//        likeRepository.save(Like.builder()
//                        .date(System.currentTimeMillis())
//
//                .build());
    }
}
