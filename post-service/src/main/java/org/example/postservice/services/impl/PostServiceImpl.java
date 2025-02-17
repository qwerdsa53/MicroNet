package org.example.postservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.postservice.models.File;
import org.example.postservice.models.Post;
import org.example.postservice.models.Tag;
import org.example.postservice.models.User;
import org.example.postservice.models.dto.PostDto;
import org.example.postservice.repo.PostRepo;
import org.example.postservice.repo.TagRepo;
import org.example.postservice.services.ImageService;
import org.example.postservice.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final TagRepo tagRepo;
    private final ImageService imageService;

    public List<PostDto> getAllPostsByUserId(Long userId) {
        return postRepo.getAllByUserId(userId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public PostDto addPostByUserId(PostDto postDto, Long userId, List<MultipartFile> files) {
        User user = new User();
        user.setId(userId);

        List<Tag> tags = postDto.getTags().stream()
                .map(tagDto -> tagRepo.findByName(tagDto)
                        .orElseGet(() -> {
                            Tag newTag = new Tag(tagDto);
                            return tagRepo.save(newTag);
                        }))
                .collect(Collectors.toList());

        Post post = Post.builder()
                .user(user)
                .tags(tags)
                .title(postDto.getTitle())
                .text(postDto.getText())
                .files(new ArrayList<>())
                .likes(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        post = postRepo.save(post);
        long postId = post.getId();

        if (files != null && !files.isEmpty()) {
            Post finalPost = post;
            files.forEach(file -> {
                String url = imageService.upload(file, userId, postId);
                finalPost.getFiles().add(new File(url, finalPost));
            });
        }

        return convertToDto(postRepo.save(post));
    }

    @Transactional
    public boolean updatePost(PostDto postDto, Long userId, List<MultipartFile> files) {
        Post post = postRepo.findByIdAndUserId(postDto.getId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found or access denied"));

        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setUpdatedAt(LocalDateTime.now());

        if (postDto.getTags() != null) {
            List<Tag> updatedTags = convertToEntity(postDto.getTags());

            post.getTags().removeAll(post.getTags());
            post.getTags().addAll(updatedTags);
        } else {
            post.setTags(new ArrayList<>());
        }

        if (files != null && !files.isEmpty()) {
            List<File> fileList = post.getFiles();
            fileList.clear();
            files.forEach(file -> {
                String url = imageService.upload(file, userId, post.getId());
                post.getFiles().add(new File(url, post));
            });
        }

        postRepo.save(post);
        return true;
    }

    @Transactional
    public boolean deletePost(Long postId, Long userId) {
        int deletedRows = postRepo.deleteByIdAndUserId(postId, userId);
        return deletedRows > 0;
    }

    public Page<PostDto> getAllPosts(int page, int size, List<String> tags) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts;

        if (tags != null && !tags.isEmpty()) {
            List<Tag> tagEntities = tagRepo.findByNameIn(tags);
            if (tagEntities.isEmpty()) {
                return Page.empty(pageable);
            }
            posts = postRepo.findByTagsIn(tagEntities, pageable);
        } else {
            posts = postRepo.findAll(pageable);
        }

        return posts.map(this::convertToDto);
    }

    private PostDto convertToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .text(post.getText())
                .cntLikes(post.getLikes() != null ?
                        post.getLikes().size() : 0)
                .tags(post.getTags() != null ?
                        post.getTags().stream().map(Tag::getName).toList() : null)
                .files(post.getFiles() != null ?
                        post.getFiles().stream().map(File::getUrl).toList() : null)
                .createdAt(post.getCreatedAt())
                .build();
    }

    private Post convertToEntity(PostDto postDto, Long userId) {
        User user = new User();
        user.setId(userId);

        List<Tag> tags = convertToEntity(postDto.getTags());
        return Post.builder()
                .user(user)
                .tags(tags)
                .title(postDto.getTitle())
                .text(postDto.getText())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private List<Tag> convertToEntity(List<String> tags) {
        return tags.stream()
                .map(tagName -> tagRepo.findByName(tagName)
                        .orElseGet(() -> new Tag(tagName)))
                .toList();
    }
}
