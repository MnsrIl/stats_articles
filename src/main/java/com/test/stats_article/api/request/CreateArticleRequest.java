package com.test.stats_article.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateArticleRequest {
    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String content;
}
