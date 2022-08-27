package dev.gniadek.githubrestdemo.utils.mappers;

import dev.gniadek.githubrestdemo.domain.github.GitHubRepo;
import dev.gniadek.githubrestdemo.dto.github.GitHubRepositoryDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitHubDTOMapper {

    public List<GitHubRepositoryDTO> mapRepositoryList(List<GitHubRepo> gitHubRepositories){
        List<GitHubRepositoryDTO> result = new ArrayList<>();
        gitHubRepositories.forEach(repo -> result.add(mapRepository(repo)));
        return result;
    }

    private GitHubRepositoryDTO mapRepository(GitHubRepo gitHubRepo){

        return GitHubRepositoryDTO.builder()
                .repositoryName(gitHubRepo.getName())
                .ownerLogin(gitHubRepo.getOwnerLogin())
                .branches(gitHubRepo.getBranches())
                .build();

    }


}
