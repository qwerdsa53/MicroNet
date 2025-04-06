package qwerdsa53.apigateway.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "services")
public class ServicesProperties {
    private String userServiceUri;
    private String postsServiceUri;
    private String feedServiceUri;
    private String mailServiceUri;
    private String wsGatewayUri;
    private String fileServiceUri;
}
