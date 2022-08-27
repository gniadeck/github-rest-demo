package dev.gniadek.githubrestdemo.dao;

import dev.gniadek.githubrestdemo.domain.github.GitHubBranch;
import dev.gniadek.githubrestdemo.domain.github.GitHubRepo;
import dev.gniadek.githubrestdemo.utils.exceptions.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@AllArgsConstructor
public class GitHubDAOImpl implements GitHubDAO{

    private RestTemplate restTemplate;
    private final String GITHUB_BASE_URL = "https://api.github.com";

    public List<GitHubRepo> getNonForkedRepositoriesWithBranches(String username){
        List<GitHubRepo> result = fetchRepositoriesOfUser(username);
        result = removeForkedRepositories(result);
        return fetchBranchesForRepositories(result);
    }

    private List<GitHubRepo> fetchRepositoriesOfUser(String username){

        ResponseEntity<GitHubRepo[]> response;
        try {
            response =
                    restTemplate.getForEntity(GITHUB_BASE_URL + "/users/" + username + "/repos", GitHubRepo[].class);
        } catch(HttpClientErrorException e){
            if(e.getMessage().contains("404 Not Found")){
                throw new UsernameNotFoundException(username);
            } else {
                throw e;
            }
        }

        return Arrays.asList(response.getBody());
    }

    private List<GitHubRepo> removeForkedRepositories(List<GitHubRepo> repos){
        List<GitHubRepo> result = new ArrayList<>(repos);
        result.removeIf(repo -> repo.getFork() == true);
        return result;
    }

    private List<GitHubRepo> fetchBranchesForRepositories(List<GitHubRepo> repos){
        repos.forEach(repo -> repo.setBranches(fetchBranchesForRepository(repo)));
        return repos;
    }

    private List<GitHubBranch> fetchBranchesForRepository(GitHubRepo gitHubRepo){

        ResponseEntity<GitHubBranch[]> response =
                restTemplate.getForEntity(GITHUB_BASE_URL + "/repos/" +
                        gitHubRepo.getOwnerLogin() + "/" + gitHubRepo.getName() + "/branches", GitHubBranch[].class);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            return Arrays.asList(response.getBody());
        } else {
            throw new RuntimeException("Error while fetching branches for repository " + gitHubRepo.getName());
        }

    }

}
