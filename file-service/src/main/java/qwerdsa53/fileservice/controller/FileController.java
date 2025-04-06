package qwerdsa53.fileservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import qwerdsa53.fileservice.model.dto.FileUrlDto;
import qwerdsa53.fileservice.service.impl.AsyncFileService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/file")
public class FileController {
    private final AsyncFileService asyncFileService;


    @PostMapping("/upload")
    public FileUrlDto uploadFile(@RequestParam("file") MultipartFile file) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = asyncFileService.uploadFile(file);
        String url = future.get();
        return new FileUrlDto(url);
    }
}
