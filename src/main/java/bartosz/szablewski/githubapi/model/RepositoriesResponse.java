package bartosz.szablewski.githubapi.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepositoriesResponse {

    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdAt;
}
