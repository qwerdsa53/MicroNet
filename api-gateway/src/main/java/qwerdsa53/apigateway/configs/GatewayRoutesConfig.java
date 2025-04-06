package qwerdsa53.apigateway.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qwerdsa53.apigateway.props.ServicesProperties;

@Configuration
@RequiredArgsConstructor
public class GatewayRoutesConfig {
    private final ServicesProperties properties;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/{version:^v[0-9]+$}/user/**")
                        .uri(properties.getUserServiceUri()))
                .route("ws-service", r -> r.path("/ws/{userId}")
                        .filters(f -> f.setPath("/ws/{userId}"))
                        .uri(properties.getWsGatewayUri()))
                .route("posts-service", r -> r.path("/api/{version:^v[0-9]+$}/posts/**")
                        .uri(properties.getPostsServiceUri()))
                .route("feed-service", r -> r.path("/api/{version:^v[0-9]+$}/feed/**")
                        .uri(properties.getFeedServiceUri()))
                .route("mail-service", r -> r.path("/api/{version:^v[0-9]+$}/mail/**")
                        .uri(properties.getMailServiceUri()))
                .route("file-service", r -> r.path("/api/{version:^v[0-9]+$}/file/**")
                        .uri(properties.getFileServiceUri()))
                .build();
    }
}