package bartosz.szablewski.githubapi.service;

import bartosz.szablewski.githubapi.model.CustomConfiguration;
import bartosz.szablewski.githubapi.model.Repositories;
import bartosz.szablewski.githubapi.model.RepositoriesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.unityt.recruitment.github.model.CustomConfiguration;
import pl.unityt.recruitment.github.model.Repositories;
import pl.unityt.recruitment.github.model.RepositoriesResponse;

@Service
@RequiredArgsConstructor
public class RepositoriesService {

    private static final String GIT_PATH = "/repos{owner}/{repo}";

    private final CustomConfiguration configuration;
    private final RestTemplate restTemplate;
    ObjectMapper objectMapper;

    public Repositories getRepositoryByOwner(String owner, String repo) {
        if (owner == null || owner.isEmpty() || repo == null || repo.isEmpty()) {
            throw new IllegalArgumentException("Owner and repo cannot be null or empty.");
        }
       return restTemplate.getForObject(configuration.getGithubUrl() + GIT_PATH, Repositories.class, owner, repo);
    }

    public RepositoriesResponse mapToRepositoriesResponse(Repositories repositories) {

        return new RepositoriesResponse().builder()
                .fullName(repositories.getFullName())
                .description(repositories.getDescription())
                .cloneUrl(repositories.getCloneUrl())
                .stars(repositories.getStars())
                .createdAt(repositories.getCreatedAt())
                .build();
    }
}
