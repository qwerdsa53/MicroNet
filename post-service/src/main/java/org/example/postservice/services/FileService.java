package org.example.postservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file, Long userId, Long postId);

    void deleteFile(String filePath);

    void deleteFolder(String folderPath);
}
