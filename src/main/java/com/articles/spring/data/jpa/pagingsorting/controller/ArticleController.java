package com.articles.spring.data.jpa.pagingsorting.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin; //EFC: Lo cambio a configurable
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.articles.spring.data.jpa.pagingsorting.model.Article;
import com.articles.spring.data.jpa.pagingsorting.service.ArticlesService;

//@CrossOrigin(origins = "http://localhost:4200") // EFC: El control de CORS //EFC: Lo cambio a configurable
@RestController
@RequestMapping("/api")
public class ArticleController {

  @Autowired
  private ArticlesService articlesService; // EFC: Inyecto el servicio.

  @GetMapping("/pagedarticled")
  public ResponseEntity<Map<String, Object>> findByPage(
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "3") int limit) {

    return articlesService.findByPage(offset, limit);
  }

  @GetMapping("/pagedarticledtitle")
  public ResponseEntity<Map<String, Object>> findByTitlePage(
      @RequestParam(defaultValue = "") String title,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "3") int limit,
      @RequestParam(defaultValue = "id,desc") String[] sort) {

    return articlesService.findByTitleContaining(title, offset, limit, sort);
  }

  @GetMapping("/articles/{id}")
  public ResponseEntity<Article> getArticlesById(@PathVariable("id") long id) {
    return articlesService.getArticlesById(id);
  }

  @PostMapping("/articles")
  public ResponseEntity<Article> createArticles(@RequestBody Article article) {
    return articlesService.createArticles(article);
  }

  @DeleteMapping("/articles/{id}")
  public ResponseEntity<HttpStatus> deleteArticles(@PathVariable("id") long id) {
    return articlesService.deleteArticles(id);
  }

}
