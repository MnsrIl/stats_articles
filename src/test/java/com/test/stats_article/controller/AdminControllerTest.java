package com.test.stats_article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.stats_article.domain.Article;
import com.test.stats_article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldReturnStatisticsWhenHaveArticles() throws Exception {
        List<String> weekDays = generateWeekPaths();

        generateMockArticles();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(weekDays.get(0)).value(0))
                .andExpect(jsonPath(weekDays.get(1)).value(0))
                .andExpect(jsonPath(weekDays.get(2)).value(0))
                .andExpect(jsonPath(weekDays.get(3)).value(0))
                .andExpect(jsonPath(weekDays.get(4)).value(0))
                .andExpect(jsonPath(weekDays.get(5)).value(0))
                .andExpect(jsonPath(weekDays.get(6)).value(2));

        destroyArticles();
    }

    private List<String> generateWeekPaths() {
        LocalDate today = LocalDate.now(),
                tomorrow = today.plusDays(1),
                weekAgo = tomorrow.minusWeeks(1);

        List<String> weekDays = new ArrayList<>(7);

        for (LocalDate currentDate = weekAgo; currentDate.isBefore(tomorrow); currentDate = currentDate.plusDays(1))
            weekDays.add("$." + currentDate);

        return weekDays;
    }

    private void generateMockArticles() {
        for (int i = 0; i < 2; i++) {
            Article article = new Article();
            article.setTitle("Mock Article " + (i + 1));
            article.setAuthor("John Doe");
            article.setContent("This is a mock article.");
            articleRepository.save(article);
        }
    }

    private void destroyArticles() {
        articleRepository.deleteAll();
    }
}
