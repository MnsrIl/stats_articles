package com.test.stats_article.controller;

import com.test.stats_article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final ArticleService articleService;

    @GetMapping("/statistics")
    public Map<String, Long> getArticleStatistics() {
        return articleService.getStatisticsByLastWeek();
    }
}
