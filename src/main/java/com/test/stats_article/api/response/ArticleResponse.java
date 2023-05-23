package com.test.stats_article.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.stats_article.domain.Article;
import lombok.Data;

@Data
public class ArticleResponse {

    private Long id;
    private String title;
    private String author;
    private String content;

    @JsonProperty("published_date")
    private String publishedDate;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.author = article.getAuthor();
        this.content = article.getContent();
        this.publishedDate = article.getPublishedDate().toString();
        this.title = article.getTitle();
    }
}
