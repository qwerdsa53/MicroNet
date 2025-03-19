package qwerdsa53.apigateway.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Centralized API Documentation")
                        .description("Aggregated OpenAPI documentation for all microservices")
                        .version("1.0"));
    }
}