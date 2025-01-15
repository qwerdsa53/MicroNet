package org.example.userservice.configs;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.model.dto.UserDto;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {RedisReactiveAutoConfiguration.class})
public class RedisConfig {
    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory jwtConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jwtConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

    @Bean
    public LettuceConnectionFactory jwtConnectionFactory() {
        String redisHost = getRedisEnv();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(6379);
        configuration.setDatabase(0);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, String> jwtRedisTemplate(LettuceConnectionFactory jwtConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jwtConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public LettuceConnectionFactory cacheConnectionFactory() {
        String redisHost = getRedisEnv();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(6379);
        configuration.setDatabase(1);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<Long, UserDto> cacheRedisTemplate(LettuceConnectionFactory cacheConnectionFactory) {
        RedisTemplate<Long, UserDto> template = new RedisTemplate<>();
        template.setConnectionFactory(cacheConnectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }


    private String getRedisEnv() {
        String redisHost = System.getenv("REDIS_HOST");
        if (redisHost == null) {
            throw new IllegalStateException("REDIS_HOST environment variable is not set");
        }
        return redisHost;
    }
}

