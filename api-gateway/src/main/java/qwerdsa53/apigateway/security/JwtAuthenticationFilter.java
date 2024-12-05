package qwerdsa53.apigateway.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistService blacklistService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Извлекаем токен из заголовка запроса
        String token = extractTokenFromRequest(exchange.getRequest());

        // Если токен не пустой и прошел валидацию
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Проверяем, не находится ли токен в черном списке
            if (!blacklistService.isTokenBlacklisted(token)) {
                // Извлекаем данные из токена
                Long userId = jwtTokenProvider.getUserIdFromToken(token);
                String username = jwtTokenProvider.getUsernameFromToken(token);
                List<String> roles = jwtTokenProvider.getRolesFromToken(token);
                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // Создаем объект пользовательских данных
                CustomUserDetails userDetails = new CustomUserDetails(
                        userId, username, null, authorities
                );

                // Создаем аутентификацию
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );

                // Настроим контекст безопасности
                return ReactiveSecurityContextHolder.getContext()
                        .flatMap(securityContext -> {
                            // Устанавливаем аутентификацию в контекст
                            securityContext.setAuthentication(authentication);
                            return chain.filter(exchange);  // продолжаем цепочку фильтров
                        })
                        .switchIfEmpty(chain.filter(exchange));  // если контекст пуст, продолжаем цепочку
            } else {
                log.warn("Token is blacklisted: {}", token);
            }
        }

        // Если токен не прошел валидацию или он в черном списке, пропускаем запрос
        return chain.filter(exchange);
    }

    /**
     * Извлекает токен из заголовка Authorization.
     * @param request запрос
     * @return токен или null, если токен не найден
     */
    private String extractTokenFromRequest(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);  // извлекаем токен после "Bearer "
        }
        return null;
    }
}
