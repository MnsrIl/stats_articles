package com.test.stats_article.service;

import com.test.stats_article.api.request.CreateArticleRequest;
import com.test.stats_article.api.response.ArticleResponse;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    ArticleResponse create(CreateArticleRequest request);

    List<ArticleResponse> list(int page, int size);

    Map<String, Long> getStatisticsByLastWeek();
}
