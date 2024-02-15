package com.articles.spring.data.jpa.pagingsorting.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.articles.spring.data.jpa.pagingsorting.model.Article;
import com.articles.spring.data.jpa.pagingsorting.repository.ArticleRepository;
import com.articles.spring.data.jpa.pagingsorting.service.ArticlesService;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public ResponseEntity<Article> getArticlesById(long id) {

        Article article = articleRepository.findByIdoriginal(id);
        System.out.println("Resultado de buscar 1 :" + article);

        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Optional<Article> tutorialData = articleRepository.findById(article.getId());

            if (tutorialData.isPresent()) {
                return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Article> createArticles(Article article) {
        try {

            Date fechaActual = new Date(); // EFC: Para registrar la fecha actual

            Article _article = articleRepository
                    .save(new Article(
                            article.getIdoriginal(),
                            article.getTitle(),
                            article.getSummary(),
                            article.getNewsSite(),
                            article.getPublishedAt(),
                            fechaActual));

            return new ResponseEntity<>(_article, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteArticles(long id) {
        try {

            System.out.println("Se va a buscar el ID original:" + id);
            Article article = articleRepository.findByIdoriginal(id);

            if (article == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            System.out.println("Se va a eliminar el ID interno:" + article.getId());
            articleRepository.deleteById(article.getId());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> findByPage(int offset, int limit) {
        try {
            List<Article> articles = new ArrayList<Article>();

            int page = offset / limit;
            Pageable paging = PageRequest.of(page, limit);

            Page<Article> pageTuts = articleRepository.findAll(paging);
            articles = pageTuts.getContent();

            String prev = "";
            String next = "";
            if (pageTuts.hasPrevious()) {
                prev = "http://localhost:8080/api/pagedarticled?offset=" + (offset - limit) + "&limit=" + limit;
            }

            if (pageTuts.hasNext()) {
                next = "http://localhost:8080/api/pagedarticled?offset=" + (offset + limit) + "&limit=" + limit;
            }

            Map<String, Object> response = new HashMap<>();

            response.put("count", pageTuts.getTotalElements());
            response.put("next", next);
            response.put("prev", prev);
            response.put("results", articles);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> findByTitleContaining(String title, int offset, int limit,
            String[] sort) {
        try {

            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Article> articles = new ArrayList<Article>();

            System.out.println("Sort.by :" + orders);

            int page = offset / limit;
            Pageable paging = PageRequest.of(page, limit, Sort.by(orders));

            Page<Article> pageTuts = articleRepository.findByTitleContaining(title, paging);
            articles = pageTuts.getContent();

            String prev = "";
            String next = "";
            if (pageTuts.hasPrevious()) {
                prev = "http://localhost:8080/api/pagedarticled?offset=" + (offset - limit) + "&limit=" + limit
                        + "@sort=" + sort;
            }

            if (pageTuts.hasNext()) {
                next = "http://localhost:8080/api/pagedarticled?offset=" + (offset + limit) + "&limit=" + limit
                        + "@sort=" + sort;
            }

            Map<String, Object> response = new HashMap<>();

            response.put("count", pageTuts.getTotalElements());
            response.put("next", next);
            response.put("prev", prev);
            response.put("results", articles);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

}
