package qwerdsa53.apigateway.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/auth/**")
                        .uri("http://localhost:8082"))
                .route("posts-server", r -> r.path("/api/v1/posts/**")
                        .uri("http://localhost:8083"))
                .route("feed-service", r -> r.path("/api/v1/feed/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}