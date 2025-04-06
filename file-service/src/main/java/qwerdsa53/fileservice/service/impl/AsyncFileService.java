package qwerdsa53.fileservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qwerdsa53.fileservice.service.FileService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncFileService {
    private final FileService fileService;

    @Async("minioExecutor")
    public CompletableFuture<String> uploadFile(MultipartFile file) {
        try {
            String url = fileService.upload(file);
            return CompletableFuture.completedFuture(url);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}