package dev.gniadek.githubrestdemo.controller;

import dev.gniadek.githubrestdemo.dao.GitHubDAO;
import dev.gniadek.githubrestdemo.utils.exceptions.UsernameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class GitHubControllerTest {

    private MockMvc mockMvc;

    private WebApplicationContext webApplicationContext;

    @MockBean
    private GitHubDAO gitHubDAO;

    @Autowired
    public GitHubControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    public void init(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();
    }

    @Test
    public void nonforkedreposEndpointShouldReturn404OnNonExistingGitHubUser() throws Exception {

        when(gitHubDAO.getNonForkedRepositoriesWithBranches("notexisting"))
                .thenThrow(UsernameNotFoundException.class);


        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/notexisting/non-forked-repos")
                        .header("Accept","application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));


    }

    @Test
    public void nonforkedreposEndpointShouldReturn406OnBadHeader() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/notexisting/non-forked-repos")
                        .header("Accept", "application/xml"))
                .andExpect(status().is(406))
                .andExpect(jsonPath("$.status").value(406));

        verifyNoInteractions(gitHubDAO);
    }

    @Test
    public void nonforkedreposEndpointShouldAskGitHubDAOWithProperData() throws Exception {

        when(gitHubDAO.getNonForkedRepositoriesWithBranches("notexisting"))
                .thenReturn(List.of());


        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/notexisting/non-forked-repos")
                        .header("Accept","application/json"))
                .andExpect(status().isOk());

        verify(gitHubDAO, times(1))
                .getNonForkedRepositoriesWithBranches("notexisting");

    }

    @Test
    public void nonforkedreposEndpointShouldReturnEmptyArrayOnNoRepositories() throws Exception {

        when(gitHubDAO.getNonForkedRepositoriesWithBranches("test"))
                .thenReturn(List.of());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/user/test/non-forked-repos")
                        .header("Accept","application/json"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("[]", result.getResponse().getContentAsString());

    }

}
