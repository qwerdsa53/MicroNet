package qwerdsa53.apigateway.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//12
@Configuration
public class GatewayRoutesConfig {
    @Value("${user.service.uri}")
    private String userServiceUri;

    @Value("${posts.service.uri}")
    private String postsServiceUri;

    @Value("${feed.service.uri}")
    private String feedServiceUri;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/auth/**")
                        .uri(userServiceUri))
                .route("posts-service", r -> r.path("/api/v1/posts/**")
                        .uri(postsServiceUri))
                .route("feed-service", r -> r.path("/api/v1/feed/**")
                        .uri(feedServiceUri))
                .build();
    }
}