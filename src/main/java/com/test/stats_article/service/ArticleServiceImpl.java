package com.test.stats_article.service;

import com.test.stats_article.api.request.CreateArticleRequest;
import com.test.stats_article.api.response.ArticleResponse;
import com.test.stats_article.domain.Article;
import com.test.stats_article.exception.ConflictException;
import com.test.stats_article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public ArticleResponse create(CreateArticleRequest request) {
        boolean isAlreadyExists = articleRepository.existsArticleByAuthorAndContentAndTitle(
                request.getAuthor(),
                request.getContent(),
                request.getTitle());

        if (isAlreadyExists)
            throw new ConflictException("Article with provided data already exists!");

        Article article = new Article();

        article.setAuthor(request.getAuthor());
        article.setContent(request.getContent());
        article.setTitle(request.getTitle());

        Article saved = articleRepository.save(article);

        return new ArticleResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> list(int page, int size) {
        Pageable pagination = PageRequest.of(page, size);
        Page<Article> articlePage = articleRepository.findAll(pagination);

        return articlePage.getContent()
                .stream()
                .map(ArticleResponse::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getStatisticsByLastWeek() {
        LocalDate today = LocalDate.now(),
                tomorrow = today.plusDays(1),
                weekAgo = tomorrow.minusWeeks(1);

        Map<String, Long> stats = new TreeMap<>();

        for (LocalDate currentDate = weekAgo; currentDate.isBefore(tomorrow); currentDate = currentDate.plusDays(1)) {
            long count = articleRepository.countByPublishedDate(currentDate);

            stats.put(currentDate.toString(), count);
        }

        return stats;
    }
}
