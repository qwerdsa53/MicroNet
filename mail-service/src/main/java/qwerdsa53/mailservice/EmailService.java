package qwerdsa53.mailservice;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TokenService tokenService;


    @Value("${YANDEX_EMAIL}")
    private String trackMyFinanceEmail;

    public void sendConfirmEmail(UserDto userDto) {
        String token = tokenService.addTokenToRedis(userDto.getId());
        sendEmail(userDto.getEmail(),
                "Welcome to CMS!",
                "Thank you for signing up. Please confirm your email: " +
                        "http://localhost:8080/api/v1/auth/confirm?token=" + token
        );
    }


    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(trackMyFinanceEmail);

        mailSender.send(message);
    }
}
