package org.example.postservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.models.dto.PostDto;
import org.example.postservice.services.LikeService;
import org.example.postservice.services.PostService;
import org.example.postservice.utiles.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<?> getPostsByUserId(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            Long userId = jwtUtil.extractUserId(authorizationHeader);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postService.getAllPostsByUserId(userId));
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPost(
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            @RequestPart("data") PostDto postDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        log.error("dto: {}, token: {}", postDto, authorizationHeader);
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        return postService.addPostByUserId(postDto, userId, files);
    }

    @PutMapping
    public ResponseEntity<?> updatePostByUserId(
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            @RequestPart("data") PostDto postDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        if (postDto.getId() == null) {
            throw new IllegalArgumentException("Post ID must not be null");
        }
        try {
            Long userId = jwtUtil.extractUserId(authorizationHeader);

            boolean isUpdated = postService.updatePost(postDto, userId, files);
            if (isUpdated) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Post updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Post not found or access denied");
            }
        } catch (Exception e) {
            log.error("Error updating post: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating post");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(
            @PathVariable("id") Long postId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            Long userId = jwtUtil.extractUserId(authorizationHeader);

            boolean isDeleted = postService.deletePost(postId, userId);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Post deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Post not found or access denied");
            }
        } catch (Exception e) {
            log.error("Error deleting post: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error deleting post");
        }
    }

    @PostMapping("/like/{id}")
    public void likePostById(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("id") Long postId
    ) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        likeService.likePost(userId, postId);
    }

    @DeleteMapping("/like/{id}")
    public void removeLikePostById(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("id") Long postId
    ) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        likeService.removeLikeFromPost(userId, postId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> tags
    ) {
        log.debug("Fetching posts with page: {}, size: {}, tags: {}", page, size, tags);
        try {
            Page<PostDto> posts = postService.getAllPosts(page, size, tags);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error: {}", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }
}
