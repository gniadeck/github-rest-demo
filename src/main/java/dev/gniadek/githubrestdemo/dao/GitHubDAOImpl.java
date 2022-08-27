package dev.gniadek.githubrestdemo.dao;

import dev.gniadek.githubrestdemo.domain.github.GitHubBranch;
import dev.gniadek.githubrestdemo.domain.github.GitHubRepo;
import dev.gniadek.githubrestdemo.utils.exceptions.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpConnectTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@AllArgsConstructor
public class GitHubDAOImpl {

    private RestTemplate restTemplate;
    private final String GITHUB_BASE_URL = "https://api.github.com";

    public List<GitHubRepo> getNonForkedRepositoriesWithBranches(String username){
        List<GitHubRepo> result = fetchRepositoriesOfUser(username);
        result.forEach(repo -> repo.setBranches(fetchBranchesForRepository(repo)));
        return removeForkedRepositories(result);
    }

    private List<GitHubRepo> fetchRepositoriesOfUser(String username){
        ResponseEntity<GitHubRepo[]> response =
                restTemplate.getForEntity(GITHUB_BASE_URL + "/users/" + username + "/repos", GitHubRepo[].class);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            return Arrays.asList(response.getBody());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private List<GitHubBranch> fetchBranchesForRepository(GitHubRepo gitHubRepo){

        ResponseEntity<GitHubBranch[]> response =
                restTemplate.getForEntity(GITHUB_BASE_URL + "/repos/" + gitHubRepo.getOwnerLogin() + "/" + gitHubRepo.getName() + "/branches", GitHubBranch[].class);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            return Arrays.asList(response.getBody());
        } else {
            throw new RuntimeException("Error while fetching branches for repository " + gitHubRepo.getName());
        }

    }

    private List<GitHubRepo> removeForkedRepositories(List<GitHubRepo> repositories){
        List<GitHubRepo> result = new ArrayList<>(repositories);
        result.removeIf(repo -> repo.getFork() == true);
        return result;
    }

}
