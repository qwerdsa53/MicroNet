package qwerdsa53.fileservice.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwerdsa53.fileservice.props.MinioProperties;
import qwerdsa53.fileservice.service.MinioBucketService;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioBucketServiceImpl implements MinioBucketService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @SneakyThrows
    @Override
    public void createBuckets() {
        for (String bucketName : minioProperties.getBuckets().values()) {
            createBucketIfNotExists(bucketName);
        }
    }

    @SneakyThrows
    private void createBucketIfNotExists(String bucketName) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());

            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }
        } catch (Exception e) {
            log.error("Error checking or creating bucket {}: {}", bucketName, e.getMessage());
            throw e;
        }
    }
}
