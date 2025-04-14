package qwerdsa53.feeddervice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qwerdsa53.feeddervice.service.FeedService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/feed")
@AllArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<?> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> tags
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(feedService.getAllPosts(page, size, tags));
        } catch (Exception e) {
            log.error("Error: {}", String.valueOf(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

}
