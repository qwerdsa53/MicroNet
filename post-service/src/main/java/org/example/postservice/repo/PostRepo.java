package org.example.postservice.repo;

import org.example.postservice.models.Post;
import org.example.postservice.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> getAllByUserId(Long UserId);

    Optional<Post> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :postId AND p.user.id = :userId")
    int deleteByIdAndUserId(
            @Param("postId") Long postId,
            @Param("userId") Long userId
    );

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByTagsIn(List<Tag> tags, Pageable pageable);


}
