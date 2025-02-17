package org.example.postservice.services.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.postservice.exceptions.ImageUploadException;
import org.example.postservice.props.MinioProperties;
import org.example.postservice.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file, Long userId, Long posId) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException("File upload failed: "
                    + e.getMessage());
        }
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("File must have name.");
        }
        String filename = generateFileName(file, userId, posId);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException("File upload failed: "
                    + e.getMessage());
        }
        saveImage(inputStream, filename);
        return getFileUrl(filename);
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

    public String getFileUrl(String filename) {
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
    private String generateFileName(MultipartFile file, Long userId, Long postId) {
        String extension = getExtension(file);
        return userId+"/"+postId+"/"+UUID.nameUUIDFromBytes(file.getBytes()) + "." + extension;
    }

    @SneakyThrows
    private void saveImage(final InputStream inputStream, final String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }
}
