package org.example.postservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.postservice.models.Like;
import org.example.postservice.models.Post;
import org.example.postservice.models.User;
import org.example.postservice.repo.LikeRepo;
import org.example.postservice.repo.PostRepo;
import org.example.postservice.services.LikeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepo likeRepo;
    private final PostRepo postRepo;
//    private final UserServiceClient userServiceClient;


    @Override
    @Transactional
    public void likePost(Long userId, Long postId) {
        User user = new User(userId);
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (likeRepo.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalArgumentException("User already liked this post");
        }
        likeRepo.save(new Like(user, post, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public void removeLikeFromPost(Long userId, Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!likeRepo.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalArgumentException("User have not like this post");
        }
        likeRepo.deleteByUserIdAndPostId(userId, postId);
    }
}
