package com.test.stats_article.controller;

import com.test.stats_article.api.request.CreateArticleRequest;
import com.test.stats_article.api.response.ArticleResponse;
import com.test.stats_article.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ArticleResponse create(@Valid @RequestBody CreateArticleRequest request) {
        return articleService.create(request);
    };

    @GetMapping
    public List<ArticleResponse> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return articleService.list(page, size);
    }
}
