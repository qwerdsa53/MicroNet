package qwerdsa53.mailservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {RedisReactiveAutoConfiguration.class})
public class RedisConfig {

    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory uuidConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(uuidConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public LettuceConnectionFactory uuidConnectionFactory() {
        String redisHost = getRedisEnv();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(6379);
        configuration.setDatabase(2);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    @DependsOn("uuidConnectionFactory")
    public RedisTemplate<String, Long> uuidRedisTemplate(LettuceConnectionFactory uuidConnectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(uuidConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return template;
    }

    private String getRedisEnv() {
        String redisHost = System.getenv("REDIS_HOST");
        log.error("Redis host: {}", redisHost);
        if (redisHost == null) {
            throw new IllegalStateException("REDIS_HOST environment variable is not set");
        }
        return redisHost;
    }

    @Bean
    public int testRedisConnection(LettuceConnectionFactory uuidConnectionFactory) {
        try {
            uuidConnectionFactory.getConnection().ping(); // Проверяем соединение
            log.info("Successfully connected to Redis");
            return 1;
        } catch (Exception e) {
            log.error("Failed to connect to Redis: {}", e.getMessage());
            return 0;
        }
    }
}
