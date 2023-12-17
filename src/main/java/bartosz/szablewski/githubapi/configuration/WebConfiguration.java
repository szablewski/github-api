package bartosz.szablewski.githubapi.configuration;

import bartosz.szablewski.githubapi.model.CustomConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration {

    private final CustomConfiguration configuration;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(configuration.getTimeoutDuration()))
                .setReadTimeout(Duration.ofMillis(configuration.getTimeoutDuration()))
                .build();
    }
}
