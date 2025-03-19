package qwerdsa53.feeddervice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final RestTemplate restTemplate;

    @Value("${post-service.url:http://posts-service:8083/api/v1/posts}")
    private String postServiceUrl;

    public Object getAllPosts(int page, int size, List<String> tags) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(postServiceUrl)
                .path("/all")
                .queryParam("page", page)
                .queryParam("size", size);

        if (tags != null && !tags.isEmpty()) {
            uriBuilder.queryParam("tags", String.join(",", tags));
        }

        String url = uriBuilder.toUriString();
        return restTemplate.getForObject(url, Object.class);
    }
}
