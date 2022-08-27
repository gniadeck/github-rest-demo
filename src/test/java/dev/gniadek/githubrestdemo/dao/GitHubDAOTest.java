package dev.gniadek.githubrestdemo.dao;

import dev.gniadek.githubrestdemo.domain.github.GitHubRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GitHubDAOTest {

    @Autowired
    GitHubDAO gitHubDAO;

    @MockBean
    RestTemplate restTemplate;


    @Test
    public void removeForkedRepositoriesShouldRemoveForkedRepositoriesIfForkedRepositoryPresent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        GitHubRepo forkedRepo = new GitHubRepo();
        forkedRepo.setFork(true);

        GitHubRepo notForkedRepo = new GitHubRepo();
        notForkedRepo.setFork(false);

        Method removeForkedRepositories = gitHubDAO.getClass().getDeclaredMethod("removeForkedRepositories", List.class);
        removeForkedRepositories.setAccessible(true);

        List<GitHubRepo> result = (List<GitHubRepo>) removeForkedRepositories.invoke(gitHubDAO, List.of(forkedRepo, notForkedRepo));

        assertEquals(1, result.size());
        assertThat(result, not(contains(forkedRepo)));

    }

    @Test
    public void getNonForkedRepositoriesShouldRemoveForkedRepositoriesIfForkedRepositoryPresent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        GitHubRepo forkedRepo = new GitHubRepo();
        forkedRepo.setFork(true);

        GitHubRepo notForkedRepo = new GitHubRepo();
        notForkedRepo.setFork(false);

        Method removeForkedRepositories = gitHubDAO.getClass().getDeclaredMethod("removeForkedRepositories", List.class);
        removeForkedRepositories.setAccessible(true);

        List<GitHubRepo> result = (List<GitHubRepo>) removeForkedRepositories.invoke(gitHubDAO, List.of(forkedRepo, notForkedRepo));

        assertEquals(1, result.size());
        assertThat(result, not(contains(forkedRepo)));

    }
}
