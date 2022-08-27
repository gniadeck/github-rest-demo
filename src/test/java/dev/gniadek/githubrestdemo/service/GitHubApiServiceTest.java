package dev.gniadek.githubrestdemo.service;

import dev.gniadek.githubrestdemo.dao.GitHubDAO;
import dev.gniadek.githubrestdemo.utils.exceptions.UsernameNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class GitHubApiServiceTest {

    @Autowired
    private GitHubApiService gitHubApiService;

    @MockBean
    private GitHubDAO gitHubDAO;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void getNonForkedReposForUserShouldThrowExceptionOnBadUsername(){

        when(gitHubDAO.getNonForkedRepositoriesWithBranches("notreal"))
                .thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class,
                () -> gitHubApiService.getNonForkedReposForUser("notreal"));


    }

}
