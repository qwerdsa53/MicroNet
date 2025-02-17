package org.example.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MailServiceClient {
    @Value("${mail-service.uri}")
    private String mailServiceUri;

    private final RestTemplate restTemplate;

    public void sendEmail(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("X-API-KEY", "SECRET");
        String mailServiceUrl = mailServiceUri+"/api/v1/mail/confirm";
        HttpEntity<String> requestEntity = new HttpEntity<>(email, headers);
        restTemplate.exchange(mailServiceUrl, HttpMethod.POST, requestEntity, String.class);
    }
}
