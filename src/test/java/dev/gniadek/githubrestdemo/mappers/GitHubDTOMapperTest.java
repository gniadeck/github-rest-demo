package dev.gniadek.githubrestdemo.mappers;

import dev.gniadek.githubrestdemo.domain.github.GitHubBranch;
import dev.gniadek.githubrestdemo.domain.github.GitHubRepo;
import dev.gniadek.githubrestdemo.dto.github.GitHubRepositoryDTO;
import dev.gniadek.githubrestdemo.utils.mappers.GitHubDTOMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GitHubDTOMapperTest {

    private GitHubDTOMapper gitHubDTOMapper;

    @Autowired
    public GitHubDTOMapperTest(GitHubDTOMapper gitHubDTOMapper) {
        this.gitHubDTOMapper = gitHubDTOMapper;
    }

    @Test
    public void gitHubDtoMapperShouldMapListOfGitHubRepos(){

        GitHubRepo repo = new GitHubRepo();
        repo.setName("test");
        repo.setOwnerLogin("login");
        repo.setBranches(List.of(new GitHubBranch()));
        repo.setFork(true);
        List<GitHubRepo> testRepos = List.of(repo);

        List<GitHubRepositoryDTO> result = gitHubDTOMapper.mapRepositoryList(testRepos);
        GitHubRepositoryDTO checkedDto = result.get(0);

        assertEquals("test", checkedDto.getRepositoryName());
        assertEquals("login", checkedDto.getOwnerLogin());
        assertEquals(List.of(new GitHubBranch()), checkedDto.getBranches());

    }

}
