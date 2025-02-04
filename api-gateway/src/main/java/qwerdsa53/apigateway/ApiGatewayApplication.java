package qwerdsa53.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApiGatewayApplication {
    //1
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
