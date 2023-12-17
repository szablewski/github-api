package bartosz.szablewski.githubapi.controller;

import bartosz.szablewski.githubapi.model.Repositories;
import bartosz.szablewski.githubapi.model.RepositoriesResponse;
import bartosz.szablewski.githubapi.service.RepositoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/repositories")
@RequiredArgsConstructor
public class RepositoriesController {

    private final RepositoriesService repositoriesService;

    @GetMapping(path = "/{owner}/{repository-name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RepositoriesResponse> getRepositories(@PathVariable(value = "owner") String owner,
                                                                @PathVariable(value = "repository-name") String repositoryName) {

        Repositories result = repositoriesService.getRepositoryByOwner(owner, repositoryName);
        return new ResponseEntity<>(repositoriesService.mapToRepositoriesResponse(result), HttpStatus.OK);
    }
}
