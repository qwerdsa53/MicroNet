package qwerdsa53.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import qwerdsa53.apigateway.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

@Component
public class UserIdPropagationFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .flatMap(auth -> {
                    Object principal = auth.getPrincipal();
                    if (principal instanceof CustomUserDetails customUser) {
                        Long userId = customUser.getId();

                        ServerHttpRequest mutatedRequest = exchange.getRequest()
                                .mutate()
                                .header("X-User-Id", String.valueOf(userId))
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange);
                    }
                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
