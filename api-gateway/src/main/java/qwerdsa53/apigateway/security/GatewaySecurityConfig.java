package qwerdsa53.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewaySecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers(
                                "api/v1/user/auth/register",
                                "api/v1/user/auth/login",
                                "api/v1/user/auth/confirm**",
                                "api/v1/file/**",
                                "/api/v1/posts/v3/api-docs/**",
                                "/api/v1/user/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs"
                        ).permitAll()
                        .matchers(exchange -> {
                            String path = exchange.getRequest().getPath().toString();
                            return path.matches("/api/v1/user(/lite)?/\\d+") ?
                                    ServerWebExchangeMatcher.MatchResult.match() : ServerWebExchangeMatcher.MatchResult.notMatch();
                        }).permitAll()

                        .pathMatchers("/api/v1/user").authenticated()
                        .anyExchange().authenticated()
                )
                .addFilterAfter(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
