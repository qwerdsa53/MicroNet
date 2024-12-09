package qwerdsa53.apigateway.configs;


import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String dotenvPath = Paths.get("api-gateway/.env").toAbsolutePath().getParent().toString();
        Dotenv dotenv = Dotenv.configure()
                .directory(dotenvPath)
                .ignoreIfMissing()
                .load();

        Map<String, Object> dotenvMap = StreamSupport.stream(dotenv.entries().spliterator(), false)
                .collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));
        environment.getPropertySources().addFirst(new MapPropertySource("dotenvProperties", dotenvMap));
    }
}
