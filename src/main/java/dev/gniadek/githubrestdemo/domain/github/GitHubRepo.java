package dev.gniadek.githubrestdemo.domain.github;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GitHubRepository {
    private String name;
    private String ownerLogin;
    private List<GitHubBranch> branches;
    private Boolean fork;

    @JsonProperty("owner")
    public void setOwnerLoginFromNestedProperty(Map<String, String> owner){
        this.ownerLogin = owner.get("login");
    }


}
