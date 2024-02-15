package com.articles.spring.data.jpa.pagingsorting.service;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.articles.spring.data.jpa.pagingsorting.model.Article;

public interface ArticlesService {

    ResponseEntity<Map<String, Object>> findByPage(int offset, int limit);

    ResponseEntity<Map<String, Object>> findByTitleContaining(String title, int offset, int limit, String[] sort);

    ResponseEntity<Article> getArticlesById(long id);

    ResponseEntity<Article> createArticles(Article article);

    ResponseEntity<HttpStatus> deleteArticles(long id);

}
