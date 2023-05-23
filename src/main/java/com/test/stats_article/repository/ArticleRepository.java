package com.test.stats_article.repository;

import com.test.stats_article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    long countByPublishedDate(LocalDate publishedDate);

    boolean existsArticleByAuthorAndContentAndTitle(String author, String content, String title);
}
