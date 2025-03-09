package org.example.userservice.services;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(MultipartFile file, Long userId) throws FileUploadException;

    void deleteFile(String filePath);

    void deleteFolder(String folderPath);
}
