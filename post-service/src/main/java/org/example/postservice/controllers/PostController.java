package org.example.postservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.models.dto.FilesUrlDto;
import org.example.postservice.models.dto.PostDto;
import org.example.postservice.services.LikeService;
import org.example.postservice.services.PostService;
import org.example.postservice.utiles.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final LikeService likeService;

    @GetMapping
    public List<PostDto> getPostsByUserId(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        return postService.getAllPostsByUserId(userId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPost(
            @RequestPart(name = "files", required = false) Optional<FilesUrlDto> files,
            @RequestPart(name = "data") PostDto postDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        List<String> filesList = files.orElse(new FilesUrlDto(Collections.emptyList())).getFiles();
        return postService.addPostByUserId(postDto, userId, filesList);
    }

    @PutMapping
    public PostDto updatePostByUserId(
            @RequestPart(name = "files", required = false) Optional<List<String>> files,
            @RequestPart(name = "data") PostDto postDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        List<String> filesList = files.orElse(Collections.emptyList());
        return postService.updatePost(postDto, userId, filesList);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(
            @PathVariable("id") Long postId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        postService.deletePost(postId, userId);
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
    public Page<PostDto> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> tags
    ) {
        return postService.getAllPosts(page, size, tags);
    }
}
