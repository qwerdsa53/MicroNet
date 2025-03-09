package org.example.postservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.postservice.exceptions.PostLikeException;
import org.example.postservice.exceptions.PostNotFoundException;
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


    @Override
    @Transactional
    public void likePost(Long userId, Long postId) {
        User user = new User(userId);
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        if (likeRepo.existsByUserIdAndPostId(userId, postId)) {
            throw new PostLikeException("User already liked this post");
        }
        try {
            likeRepo.save(new Like(user, post, LocalDateTime.now()));
        } catch (Exception e) {
            throw new PostLikeException("Error while liking post");
        }
    }

    @Override
    @Transactional
    public void removeLikeFromPost(Long userId, Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        if (!likeRepo.existsByUserIdAndPostId(userId, postId)) {
            throw new PostLikeException("User have not like this post");
        }
        try {
            likeRepo.deleteByUserIdAndPostId(userId, postId);
        } catch (Exception e) {
            throw new PostLikeException("Post like exception");
        }
    }
}
