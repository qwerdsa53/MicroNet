package org.example.postservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.dto.PostDto;
import org.example.postservice.services.PostService;
import org.example.postservice.utiles.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> getPostsByUserId(@RequestHeader("Authorization") String authorizationHeader) {
        log.error("Authorization Header: {}", authorizationHeader);
        try {
            String token = extractToken(authorizationHeader);
            Long userId = jwtUtil.extractUserId(token);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllByUserId(userId));
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = extractToken(authorizationHeader);
            Long userId = jwtUtil.extractUserId(token);
            postService.addPostByUserId(postDto, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created");
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePostByUserId(@RequestBody PostDto postDto, @RequestHeader("Authorization") String authorizationHeader) {
        if (postDto.getId() == null) {
            throw new IllegalArgumentException("Post ID must not be null");
        }
        log.error("Post: {}", postDto);
        try {
            String token = extractToken(authorizationHeader);
            Long userId = jwtUtil.extractUserId(token);

            boolean isUpdated = postService.updatePost(postDto, userId);
            if (isUpdated) {
                return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found or access denied");
            }
        } catch (Exception e) {
            log.error("Error updating post: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating post");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") Long postId, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = extractToken(authorizationHeader);
            Long userId = jwtUtil.extractUserId(token);

            boolean isDeleted = postService.deletePost(postId, userId);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found or access denied");
            }
        } catch (Exception e) {
            log.error("Error deleting post: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting post");
        }
    }


    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
    }
}
