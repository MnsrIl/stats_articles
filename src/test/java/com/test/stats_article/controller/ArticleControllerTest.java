package com.test.stats_article.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.stats_article.api.request.CreateArticleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private RequestBuilder request(MockHttpServletRequestBuilder httpRequest, Object content) throws JsonProcessingException {
        return httpRequest.contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content));
    }

    @Test
    public void shouldFailWhenRequestWithEmptyField() throws Exception {
        // Test case: Empty field
        CreateArticleRequest emptyFieldRequest = new CreateArticleRequest();
        emptyFieldRequest.setTitle("Sample Article");
        emptyFieldRequest.setAuthor("");
        emptyFieldRequest.setContent("This is a sample article.");

        mockMvc.perform(request(post("/api/articles"), emptyFieldRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.author").exists())
                .andExpect(jsonPath("$.error.author").value("must not be blank"));


    }

    @Test
    public void shouldFailWhenAlreadyExists() throws Exception {
        // Test case: Article already exists
        CreateArticleRequest existingArticleRequest = new CreateArticleRequest();
        existingArticleRequest.setTitle("Sample Article");
        existingArticleRequest.setAuthor("John Duck");
        existingArticleRequest.setContent("This is a sample article.");

        mockMvc.perform(request(post("/api/articles"), existingArticleRequest));

        mockMvc.perform(request(post("/api/articles"), existingArticleRequest))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Article with provided data already exists"));
    }

    @Test
    public void shouldFailWhenExceedTitleChars() throws Exception {
        // Test case: Title exceeds 100 characters
        CreateArticleRequest longTitleRequest = new CreateArticleRequest();
        longTitleRequest.setTitle("This is a very long title exceeding the limit of 100 characters. This is a very long title exceeding the limit of 100 characters. This is a very long title exceeding the limit of 100 characters. This is a very long title exceeding the limit of 100 characters. This is a very long title exceeding the limit of 100 characters.");
        longTitleRequest.setAuthor("John Doe");
        longTitleRequest.setContent("This is a sample article.");

        mockMvc.perform(request(post("/api/articles"), longTitleRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.title").exists())
                .andExpect(jsonPath("$.error.title").value("Title length must be between 0 and 100 characters"));
    }

    @Test
    public void shouldReturnArticleWhenCreated() throws Exception {
        // Default case: Valid request
        CreateArticleRequest validRequest = new CreateArticleRequest();
        validRequest.setTitle("Sample Article");
        validRequest.setAuthor("John Doe");
        validRequest.setContent("This is a sample article.");

        mockMvc.perform(request(post("/api/articles"), validRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Sample Article"))
                .andExpect(jsonPath("$.author").value("John Doe"))
                .andExpect(jsonPath("$.content").value("This is a sample article."))
                .andExpect(jsonPath("$.published_date").value(LocalDate.now().toString()));
    }
}
