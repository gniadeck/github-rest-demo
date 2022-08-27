package dev.gniadek.githubrestdemo.dto.github;

import dev.gniadek.githubrestdemo.domain.github.GitHubBranch;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GitHubRepositoryDTO {

    private String repositoryName;
    private String ownerLogin;
    private List<GitHubBranch> branches;

}
