package qwerdsa53.mailservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {
    private final EmailService emailService;

    @PostMapping("/confirm")
    public ResponseEntity<String> sendConfirmEmail(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody String email
    ) {
        log.info("API Key received in Controller: {}", apiKey);
        try {
            emailService.sendConfirmEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body("Email sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
