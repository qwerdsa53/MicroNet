package qwerdsa53.fileservice.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private Map<String, String> buckets;
    private String url;
    private String accessKey;
    private String secretKey;
}
