package qwerdsa53.mailservice.setvice;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import qwerdsa53.mailservice.props.MailProperties;

@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final TokenService tokenService;
    private final MailProperties mailProperties;

    public void sendConfirmEmail(String email) {
        String token = tokenService.addTokenToRedis(email);
        sendEmail(email,
                "Welcome to CMS!",
                "Thank you for signing up. Please confirm your email: " +
                        "http://localhost:8080/api/v1/user/auth/confirm?token=" + token
        );
    }


    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(mailProperties.getUsername());

        mailSender.send(message);
    }
}
