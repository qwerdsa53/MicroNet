//package org.example.userservice.integration.services.impl;
//
//import org.example.userservice.JwtTokenProvider;
//import org.example.userservice.repo.ImageRepo;
//import org.example.userservice.repo.UserRepository;
//import qwerdsa53.fileservice.service.ImageService;
//import org.example.userservice.services.impl.MailServiceClient;
//import org.example.userservice.services.impl.TokenServiceImpl;
//import org.example.userservice.utiles.JwtUtil;
//import org.example.userservice.utiles.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest()
//@ActiveProfiles("test")
//@Testcontainers
//public class UserServiceImplIntegrationTest {
//
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("test")
//            .withUsername("test")
//            .withPassword("test");
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    private TokenServiceImpl tokenService;
//
//    @Autowired
//    private ImageService imageService;
//
//    @Autowired
//    private MailServiceClient mailServiceClient;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private ImageRepo imageRepo;
//
//    @Autowired
//    private Mapper mapper;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//
//}
