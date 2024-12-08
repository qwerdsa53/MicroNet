package org.example.postservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.dto.PostDto;
import org.example.postservice.models.Post;
import org.example.postservice.models.Tag;
import org.example.postservice.models.User;
import org.example.postservice.repo.PostRepo;
import org.example.postservice.repo.TagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final TagRepository tagRepository;

    public List<Post> getAllByUserId(Long userId) {
        return postRepo.getAllByUserId(userId);
    }

    public void addPostByUserId(PostDto postDto, Long userId) {
        User user = new User();
        user.setId(userId);

        List<Tag> tags = postDto.getTags().stream()
                .map(tagDto -> tagRepository.findByName(tagDto.getName())
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagDto.getName());
                            return tagRepository.save(newTag);
                        }))
                .toList();

        Post post = Post.builder()
                .user(user)
                .tags(tags)
                .title(postDto.getTitle())
                .text(postDto.getText())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRepo.save(post);
    }

    @Transactional
    public boolean updatePost(PostDto postDto, Long userId) {
        Post post = postRepo.findByIdAndUserId(postDto.getId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found or access denied"));

        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setUpdatedAt(LocalDateTime.now());

        if (postDto.getTags() != null) {
            List<Tag> updatedTags = postDto.getTags().stream()
                    .map(tagName -> tagRepository.findByName(tagName.getName())
                            .orElseGet(() -> new Tag(null, tagName.getName(), null)))
                    .toList();

            post.getTags().clear();
            post.getTags().addAll(updatedTags);
        }

        postRepo.save(post);
        return true;
    }

    @Transactional
    public boolean deletePost(Long postId, Long userId) {
        int deletedRows = postRepo.deleteByIdAndUserId(postId, userId);
        return deletedRows > 0;
    }
}
