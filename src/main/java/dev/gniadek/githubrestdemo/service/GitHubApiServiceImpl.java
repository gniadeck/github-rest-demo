package dev.gniadek.githubrestdemo.service;

import dev.gniadek.githubrestdemo.dao.GitHubDAO;
import dev.gniadek.githubrestdemo.dto.github.GitHubRepositoryDTO;
import dev.gniadek.githubrestdemo.utils.mappers.GitHubDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GitHubApiServiceImpl implements GitHubApiService{

    private GitHubDAO gitHubDAO;
    private GitHubDTOMapper gitHubDTOMapper;

    public List<GitHubRepositoryDTO> getNonForkedReposForUser(String username){
        return gitHubDTOMapper.mapRepositoryList(gitHubDAO.getNonForkedRepositoriesWithBranches(username));
    }


}
