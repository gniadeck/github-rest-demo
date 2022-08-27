package dev.gniadek.githubrestdemo.service;

import dev.gniadek.githubrestdemo.dto.github.GitHubRepositoryDTO;

import java.util.List;

public interface GitHubApiService {
    public List<GitHubRepositoryDTO> getNonForkedReposForUser(String username);
}
