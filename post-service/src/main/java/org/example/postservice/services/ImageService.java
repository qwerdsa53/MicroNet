package org.example.postservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(MultipartFile file, Long userId, Long postId);
}
