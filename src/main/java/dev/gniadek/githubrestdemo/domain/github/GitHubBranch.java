package dev.gniadek.githubrestdemo.domain.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class GitHubBranch {

    private String name;
    private String lastCommitSha;

    @JsonProperty("commit")
    private void setCommitSha(Map<String, String> commit){
        lastCommitSha = commit.get("sha");
    }

}
