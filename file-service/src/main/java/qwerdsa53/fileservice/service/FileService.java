package qwerdsa53.fileservice.service;


import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface FileService {
    CompletableFuture<String> upload(MultipartFile file, String bucket) throws FileUploadException;

    void deleteFile(String filePath, String bucket);
}
