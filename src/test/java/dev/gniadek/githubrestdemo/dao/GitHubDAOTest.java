package dev.gniadek.githubrestdemo.dao;

import dev.gniadek.githubrestdemo.domain.github.GitHubBranch;
import dev.gniadek.githubrestdemo.domain.github.GitHubRepo;
import dev.gniadek.githubrestdemo.utils.exceptions.UsernameNotFoundException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class GitHubDAOTest {


    GitHubDAO gitHubDAO;

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    public GitHubDAOTest(GitHubDAO gitHubDAO) {
        this.gitHubDAO = gitHubDAO;
    }

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
    public void removeForkedRepositoriesShouldRemoveForkedRepositoriesIfForkedRepositoryNotPresent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        GitHubRepo notForkedRepo = new GitHubRepo();
        notForkedRepo.setFork(false);

        GitHubRepo secondNotForkedRepo = new GitHubRepo();
        secondNotForkedRepo.setFork(false);

        Method removeForkedRepositories = gitHubDAO.getClass().getDeclaredMethod("removeForkedRepositories", List.class);
        removeForkedRepositories.setAccessible(true);

        List<GitHubRepo> result = (List<GitHubRepo>) removeForkedRepositories.invoke(gitHubDAO, List.of(notForkedRepo, secondNotForkedRepo));

        assertEquals(2, result.size());
        assertThat(result, (contains(notForkedRepo, secondNotForkedRepo)));

    }

    @Test
    public void fetchRepositoriesOfUserShouldCallProperGitHubApiEndpoint() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        when(restTemplate.getForEntity("https://api.github.com/users/test/repos", GitHubRepo[].class))
                .thenReturn(ResponseEntity.ok(new GitHubRepo[]{}));

        Method fetchRepositoriesOfUser = gitHubDAO.getClass().getDeclaredMethod("fetchRepositoriesOfUser", String.class);
        fetchRepositoriesOfUser.setAccessible(true);

        fetchRepositoriesOfUser.invoke(gitHubDAO, "test");

    }

    @Test
    public void fetchRepositoriesOfUserShouldThrowUsernameNotFoundExceptionOn404ApiResponse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        when(restTemplate.getForEntity("https://api.github.com/users/test/repos", GitHubRepo[].class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "404 Not Found"));

        Method fetchRepositoriesOfUser = gitHubDAO.getClass().getDeclaredMethod("fetchRepositoriesOfUser", String.class);
        fetchRepositoriesOfUser.setAccessible(true);

        Throwable throwable = assertThrows(Exception.class, () -> fetchRepositoriesOfUser.invoke(gitHubDAO, "test"));
        assertEquals(UsernameNotFoundException.class, throwable.getCause().getClass());

    }

    @Test
    public void fetchRepositoriesOfUserShouldThrowExceptionOnBadApiResponse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        when(restTemplate.getForEntity("https://api.github.com/users/test/repos", GitHubRepo[].class))
                .thenThrow(new RuntimeException());

        Method fetchRepositoriesOfUser = gitHubDAO.getClass().getDeclaredMethod("fetchRepositoriesOfUser", String.class);
        fetchRepositoriesOfUser.setAccessible(true);

        Throwable throwable = assertThrows(Exception.class, () -> fetchRepositoriesOfUser.invoke(gitHubDAO, "test"));
        assertNotEquals(UsernameNotFoundException.class, throwable.getCause().getClass());

    }

    @Test
    public void fetchBranchesForRepositoryShouldCallProperGitHubApiEndpoint() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        GitHubRepo input = new GitHubRepo();
        input.setName("testName");
        input.setOwnerLogin("testLogin");

        when(restTemplate.getForEntity("https://api.github.com/repos/testLogin/testName/branches", GitHubBranch[].class))
                .thenReturn(ResponseEntity.ok(new GitHubBranch[]{}));

        Method fetchRepositoriesOfUser = gitHubDAO.getClass().getDeclaredMethod("fetchBranchesForRepository", GitHubRepo.class);
        fetchRepositoriesOfUser.setAccessible(true);

        fetchRepositoriesOfUser.invoke(gitHubDAO, input);

        verify(restTemplate, times(1))
                .getForEntity("https://api.github.com/repos/testLogin/testName/branches", GitHubBranch[].class);

    }


}
