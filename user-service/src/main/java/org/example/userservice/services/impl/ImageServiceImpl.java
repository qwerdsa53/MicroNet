package org.example.userservice.services.impl;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.props.MinioProperties;
import org.example.userservice.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file, Long userId) throws FileUploadException {
        try {
            createBucket();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed: "
                    + e.getMessage());
        }
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileUploadException("File must have name.");
        }
        String filename = generateFileName(file, userId);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed: "
                    + e.getMessage());
        }
        saveImage(inputStream, filename);
        return getFileUrl(filename);
    }

    @Override
    public void deleteFile(String filePath) {
        if (filePath.contains("https://") || filePath.contains("http://")) {
            filePath = extractFilePath(filePath, minioProperties.getBucket());
        }
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(filePath)
                            .build()
            );
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error while deleting file from MinIO: " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteFolder(String userId) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .prefix(userId + "/")
                            .recursive(true)
                            .build()
            );
            for (Result<Item> result : results) {
                String filePath = result.get().objectName();
                deleteFile(filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting folder: " + e.getMessage(), e);
        }
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());

        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String getFileUrl(String filename) {
        return String.format("%s/%s/%s",
                minioProperties.getUrl(),
                minioProperties.getBucket(),
                filename);
    }

    private String getExtension(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private String generateFileName(MultipartFile file, Long userId) {
        String extension = getExtension(file);
        StringBuffer fileName = new StringBuffer();
        fileName.append(userId)
                .append("/")
                .append(UUID.nameUUIDFromBytes(file.getBytes()))
                .append(".")
                .append(extension);

        return fileName.toString();
    }

    @SneakyThrows
    private void saveImage(final InputStream inputStream, final String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
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

    private String extractFolderPath(String fileUrl, String bucketName) {
        try {
            URI uri = new URI(fileUrl);
            String path = uri.getPath();

            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (path.startsWith(bucketName + "/")) {
                path = path.substring(bucketName.length() + 1);
            }

            int lastSlashIndex = path.lastIndexOf('/');
            if (lastSlashIndex > 0) {
                return path.substring(0, lastSlashIndex);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while getting folder path from URL: " + fileUrl, e);
        }
        return null;
    }

}
