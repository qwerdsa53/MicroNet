package org.example.postservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.exceptions.*;
import org.example.postservice.models.File;
import org.example.postservice.models.Post;
import org.example.postservice.models.Tag;
import org.example.postservice.models.User;
import org.example.postservice.models.dto.PostDto;
import org.example.postservice.repo.FileRepo;
import org.example.postservice.repo.PostRepo;
import org.example.postservice.repo.TagRepo;
import org.example.postservice.services.FileService;
import org.example.postservice.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final TagRepo tagRepo;
    private final FileRepo fileRepo;
    private final FileService fileService;

    public List<PostDto> getAllPostsByUserId(Long userId) {
        try {
            return postRepo.getAllByUserId(userId).stream()
                    .map(this::convertToDto)
                    .toList();
        } catch (Exception e) {
            throw new GetPostException("Error while getting user posts");
        }
    }

    @Transactional
    public PostDto addPostByUserId(PostDto postDto, Long userId, List<String> urls) {
        User user = new User();
        user.setId(userId);

        List<Tag> tags = Optional.ofNullable(postDto.getTags())
                .orElse(Collections.emptyList())
                .stream()
                .distinct()
                .map(tagDto -> tagRepo.findByName(tagDto)
                        .orElseGet(() -> tagRepo.save(new Tag(tagDto)))
                )
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


        try {
            if (urls != null && !urls.isEmpty()) {
                for (String url : urls) {
                    post.getFiles().add(new File(
                            url,
                            LocalDateTime.now(),
                            post));
                }
            }
            return convertToDto(post);
        } catch (FileUploadException e) {
            fileService.deleteFolder(post.getFiles().get(0).getUrl());
            throw new FileUploadException("Error uploading files: " + e.getMessage());
        } catch (Exception e) {
            throw new PostSaveException("Error while saving post");
        }
    }

    @Transactional
    public PostDto updatePost(PostDto postDto, Long userId, List<String> urls) {
        Post post = postRepo.findByIdAndUserId(postDto.getId(), userId)
                .orElseThrow(() -> new PostNotFoundException("Post not found or access denied"));

        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setUpdatedAt(LocalDateTime.now());

        if (postDto.getTags() != null) {
            List<Tag> updatedTags = convertToEntity(postDto.getTags());

            post.getTags().clear();
            post.getTags().addAll(updatedTags);
        } else {
            post.setTags(new ArrayList<>());
        }

        if (!post.getFiles().isEmpty()) {
            fileService.deleteFolder(post.getFiles().get(0).getUrl());
        }
        fileRepo.deleteAll(post.getFiles());
        post.getFiles().clear();


        if (urls != null) {
            for (String url : urls) {
                post.getFiles().add(new File(
                        url,
                        LocalDateTime.now(),
                        post));
            }
        }
        return convertToDto(post);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        try {
            Post post = postRepo.findByIdAndUserId(postId, userId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found or access denied"));
            post.getTags().clear();
            if (!post.getFiles().isEmpty()) {
                fileService.deleteFolder(post.getFiles().get(0).getUrl());
            }
            postRepo.delete(post);
        } catch (PostNotFoundException e) {
            throw new PostNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new PostDeleteException("Error while deleting post");
        }
    }

    public Page<PostDto> getAllPosts(int page, int size, List<String> tags) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts;

        try {
            if (tags != null && !tags.isEmpty()) {
                List<Tag> tagEntities = tagRepo.findByNameIn(tags);
                if (tagEntities.isEmpty()) {
                    return Page.empty(pageable);
                }
                posts = postRepo.findByTagsIn(tagEntities, pageable);
            } else {
                posts = postRepo.findAll(pageable);
            }
        } catch (Exception e) {
            throw new FeedException("Error while creating feed");
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
