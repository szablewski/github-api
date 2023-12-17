package bartosz.szablewski.githubapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.configuration")
public class CustomConfiguration {

    private int timeoutDuration;
    private String secretKey;
    private String githubUrl;
}
