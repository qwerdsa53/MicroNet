package qwerdsa53.fileservice.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qwerdsa53.fileservice.props.MinioProperties;
import qwerdsa53.fileservice.service.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    @Async("minioExecutor")
    public CompletableFuture<String> upload(MultipartFile file, String bucket) throws FileUploadException {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileUploadException("File must have name.");
        }
        String filename = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed: "
                    + e.getMessage());
        }
        saveFile(inputStream, file, filename, bucket);
        return CompletableFuture.completedFuture(getFileUrl(filename, bucket));
    }

    @Override
    public void deleteFile(String filePath, String bucket) {
        if (filePath.contains("https://") || filePath.contains("http://")) {
            filePath = extractFilePath(filePath, bucket);
        }
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(filePath)
                            .build()
            );
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error while deleting file from MinIO: " + e.getMessage(), e);
        }
    }

    private String getFileUrl(String filename, String bucket) {
        return String.format("%s/%s/%s",
                minioProperties.getUrl(),
                bucket,
                filename);
    }

    private String getExtension(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return System.currentTimeMillis() + "-" + UUID.randomUUID() + "." + extension;
    }

    @SneakyThrows
    private void saveFile(
            final InputStream inputStream,
            final MultipartFile file,
            final String fileName,
            String bucket
    ) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, file.getSize(), -1)
                .bucket(bucket)
                .object(fileName)
                .build());
    }

    private String extractFilePath(String filePath, String bucketName) {
        try {
            URI uri = new URI(filePath);
            String path = uri.getPath();

            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (path.startsWith(bucketName + "/")) {
                path = path.substring(filePath.length() + 1);
            }
            log.error("FilePath: {}", path);
            return path;

        } catch (Exception e) {
            throw new RuntimeException("Error while getting folder path from URL: " + filePath, e);
        }
    }
}
