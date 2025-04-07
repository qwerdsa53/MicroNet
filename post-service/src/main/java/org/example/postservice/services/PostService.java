package org.example.postservice.services;

import org.example.postservice.models.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPostsByUserId(Long userId);

    PostDto addPostByUserId(PostDto postDto, Long userId, List<String> files);

    PostDto updatePost(PostDto postDto, Long userId, List<String> files);

    void deletePost(Long postId, Long userId);

    Page<PostDto> getAllPosts(int page, int size, List<String> tags);
}
