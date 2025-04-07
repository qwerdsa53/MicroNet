package qwerdsa53.fileservice.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwerdsa53.fileservice.service.MinioBucketService;
import qwerdsa53.fileservice.service.MinioInitService;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioInitServiceImpl implements MinioInitService {
    private final MinioBucketService minioBucketService;

    @Override
    @PostConstruct
    public void init() {
        minioBucketService.createBuckets();
    }
}
