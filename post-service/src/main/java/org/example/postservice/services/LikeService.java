package org.example.postservice.services;

public interface LikeService {
    void likePost(Long userId, Long postId);

    void removeLikeFromPost(Long userId, Long postId);
}
