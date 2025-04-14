package org.example.userservice.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import qwerdsa53.shared.model.dto.MailRequestDto;
import qwerdsa53.shared.model.type.MailType;

@Service
public class MailServiceClient {
    private final KafkaTemplate<String, MailRequestDto> kafkaTemplate;

    private static final String MAIL_SERVICE_TOPIC = "mail-service";

    public MailServiceClient(@Qualifier("mailKafkaTemplate") KafkaTemplate<String, MailRequestDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWelcomeEmail(String email) {
        MailRequestDto mailRequestDto = createMailRequest(email);

        kafkaTemplate.send(MAIL_SERVICE_TOPIC, mailRequestDto);
    }

    private MailRequestDto createMailRequest(String email) {
        return new MailRequestDto(email, MailType.REGISTRATION);
    }
}
