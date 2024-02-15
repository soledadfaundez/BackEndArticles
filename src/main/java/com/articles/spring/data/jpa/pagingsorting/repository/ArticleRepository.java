package com.articles.spring.data.jpa.pagingsorting.repository;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.articles.spring.data.jpa.pagingsorting.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Page<Article> findAll(Pageable pageable);

  Article findByIdoriginal(long idoriginal);
  // getIdoriginal

  // Page<Article> findByPublished(boolean published, Pageable pageable);

  Page<Article> findByTitleContaining(String title, Pageable pageable);

  // List<Article> findByTitleContaining(String title, Sort sort);
}
