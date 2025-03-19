package org.example.userservice.integration.services.impl;


import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.BlackListService;
import org.example.userservice.utiles.RedisForStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@ActiveProfiles("test")
@Testcontainers
class BlackListServiceImplIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private BlackListService blackListService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisForStatus redis;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User requester = User.builder()
                .username("requester")
                .password("pass")
                .email("req@example.com")
                .enabled(true)
                .userBlackList(new HashSet<>())
                .lastSeen(LocalDateTime.now())
                .build();
        User target = User.builder()
                .username("target")
                .password("pass")
                .email("target@example.com")
                .enabled(true)
                .userBlackList(new HashSet<>())
                .lastSeen(LocalDateTime.now())
                .build();

        userRepository.save(requester);
        userRepository.save(target);
    }

    @Test
    void addToBlackList_and_getFromBlackList() {
        User requester = userRepository.findByUsername("requester").get();
        User target = userRepository.findByUsername("target").get();

        blackListService.addToBlackList(requester.getId(), target.getId());
        Page<LiteUserDto> blackList = blackListService.getBlackList(requester.getId(), 0, 10);
        assertEquals(1, blackList.getTotalElements());
        assertEquals("target", blackList.getContent().get(0).getUsername());
    }

    @Test
    void removeFromBlackList() {
        User requester = userRepository.findByUsername("requester").get();
        User target = userRepository.findByUsername("target").get();

        blackListService.addToBlackList(requester.getId(), target.getId());
        blackListService.removeFromBlackList(requester.getId(), target.getId());

        Page<LiteUserDto> blackList = blackListService.getBlackList(requester.getId(), 0, 10);
        assertTrue(blackList.isEmpty());
    }
}
