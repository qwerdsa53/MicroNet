package qwerdsa53.fileservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import qwerdsa53.fileservice.model.dto.FileUrlDto;
import qwerdsa53.fileservice.props.MinioProperties;
import qwerdsa53.fileservice.service.FileService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/file")
public class FileController {
    private final FileService fileService;
    private final MinioProperties minioProperties;

    @PostMapping("/upload/avatar")
    public FileUrlDto uploadAvatar(@RequestParam("file") MultipartFile file) throws
            ExecutionException,
            InterruptedException,
            FileUploadException {
        CompletableFuture<String> future = fileService.upload(
                file,
                minioProperties.getBuckets().get("avatarBucket")
        );
        String url = future.get();
        return new FileUrlDto(url);
    }

    @PostMapping("/upload/post-file")
    public FileUrlDto uploadPostFile(@RequestParam("file") MultipartFile file) throws
            ExecutionException,
            InterruptedException,
            FileUploadException {
        CompletableFuture<String> future = fileService.upload(
                file,
                minioProperties.getBuckets().get("fileBucket")
        );
        String url = future.get();
        return new FileUrlDto(url);
    }
}
