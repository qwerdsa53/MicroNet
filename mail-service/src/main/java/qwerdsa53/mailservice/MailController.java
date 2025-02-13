package qwerdsa53.mailservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {
    private final EmailService emailService;

    @PostMapping("/send/confirm")
    public ResponseEntity<String> sendConfirmEmail(@RequestBody String username) {
        try {
            emailService.sendConfirmEmail(username);
            return ResponseEntity.status(HttpStatus.OK).body("Email sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
