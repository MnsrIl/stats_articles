package com.test.stats_article.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateArticleRequest {

    @NotBlank
    @Length(max = 100, message = "Title length must be between 0 and 100 characters")
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String content;
}
