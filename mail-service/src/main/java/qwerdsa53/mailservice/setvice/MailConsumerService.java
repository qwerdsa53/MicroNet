package qwerdsa53.mailservice.setvice;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import qwerdsa53.shared.model.dto.MailRequestDto;

@Service
@RequiredArgsConstructor
public class MailConsumerService {
    private final MailService mailService;

    @KafkaListener(topics = "mail-service", groupId = "mail-group")
    public void listen(MailRequestDto mailRequestDto) {
        switch (mailRequestDto.getMailType()) {
            case REGISTRATION:
                mailService.sendConfirmEmail(mailRequestDto.getRecipient());
                break;
            case PASSWORD_RESET:
                //TODO
                break;
            case NOTIFICATION:
                //TODO
                break;
            default:
                throw new IllegalArgumentException("Unsupported mail type: " + mailRequestDto.getMailType());
        }
    }
}
