package org.example.postservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.models.Post;
import org.example.postservice.models.Tag;
import org.example.postservice.models.User;
import org.example.postservice.models.dto.PostDto;
import org.example.postservice.repo.PostRepo;
import org.example.postservice.repo.TagRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final TagRepo tagRepo;

    public List<PostDto> getAllPostsByUserId(Long userId) {
        return postRepo.getAllByUserId(userId).stream()
                .map(this::convertToDto)
                .toList();
    }

    public void addPostByUserId(PostDto postDto, Long userId) {
        User user = new User();
        user.setId(userId);

        List<Tag> tags = postDto.getTags().stream()
                .map(tagDto -> tagRepo.findByName(tagDto)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagDto);
                            return tagRepo.save(newTag);
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
                    .map(tagName -> tagRepo.findByName(tagName)
                            .orElseGet(() -> new Tag(null, tagName, null)))
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

    public Page<PostDto> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts = postRepo.findAll(pageable);
        return posts.map(this::convertToDto);
    }

    private PostDto convertToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .text(post.getText())
                .tags(post.getTags() != null ?
                        post.getTags().stream().map(Tag::getName).toList() : null)
                .createdAt(post.getCreatedAt())
                .build();
    }
}
