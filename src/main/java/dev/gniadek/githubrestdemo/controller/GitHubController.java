package dev.gniadek.githubrestdemo.controller;

import dev.gniadek.githubrestdemo.dto.github.GitHubRepositoryDTO;
import dev.gniadek.githubrestdemo.service.GitHubApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class GitHubController {

    private GitHubApiService githubApiService;

    @GetMapping("/user/{username}/non-forked-repos")
    public ResponseEntity<List<GitHubRepositoryDTO>> getNonForkedRepos(@PathVariable("username") String username){
        return ResponseEntity.ok(githubApiService.getNonForkedReposForUser(username));
    }

}
