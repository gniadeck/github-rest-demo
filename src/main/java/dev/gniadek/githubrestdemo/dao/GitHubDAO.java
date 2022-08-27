package dev.gniadek.githubrestdemo.dao;

import dev.gniadek.githubrestdemo.domain.github.GitHubRepo;

import java.util.List;

public interface GitHubDAO {
    public List<GitHubRepo> getNonForkedRepositoriesWithBranches(String username);
}
