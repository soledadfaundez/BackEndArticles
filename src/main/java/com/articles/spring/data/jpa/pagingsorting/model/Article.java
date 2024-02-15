package com.articles.spring.data.jpa.pagingsorting.model;

import java.util.Date;

//import javax.persistence.*; // for Spring Boot 2
import javax.persistence.*;

@Entity
@Table(name = "article")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "idoriginal")
  private long idoriginal;

  @Column(name = "title", length = 250)
  private String title;

  @Column(name = "summary", length = 1000)
  private String summary;

  @Column(name = "news_site", length = 1000)
  private String newsSite;

  @Column(name = "published_at")
  private Date publishedAt;

  @Column(name = "favorited_at")
  private Date favoritedAt;

  public Article() {

  }

  public Article(long idoriginal, String title, String summary, String newsSite, Date publishedAt, Date favoritedAt) {
    this.idoriginal = idoriginal;
    this.title = title;
    this.summary = summary;
    this.newsSite = newsSite;
    this.publishedAt = publishedAt;
    this.favoritedAt = favoritedAt;
  }

  public long getId() {
    return id;
  }

  // idoriginal
  public long getIdoriginal() {
    return idoriginal;
  }

  public void getIdoriginal(long idoriginal) {
    this.idoriginal = idoriginal;
  }

  // title
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    // this.title = title;
    if (title != null && title.length() > 250) {
      // Truncar el texto al límite permitido
      this.title = title.substring(0, 250);
    } else {
      this.title = title;
    }
  }

  // summary
  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    // this.summary = summary;

    // Verificar si el texto es más largo que el límite permitido
    if (summary != null && summary.length() > 1000) {
      // Truncar el texto al límite permitido
      this.summary = summary.substring(0, 1000);
    } else {
      this.summary = summary;
    }
  }

  // newsSite
  public String getNewsSite() {
    return newsSite;
  }

  public void setNewsSite(String newsSite) {
    // this.newsSite = newsSite;

    // Verificar si el texto es más largo que el límite permitido
    if (newsSite != null && newsSite.length() > 1000) {
      // Truncar el texto al límite permitido
      this.newsSite = newsSite.substring(0, 1000);
    } else {
      this.newsSite = newsSite;
    }
  }

  // publishedAt
  public Date getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Date publishedAt) {
    this.publishedAt = publishedAt;
  }

  // favoritedAt
  public Date getFavoritedAt() {
    return favoritedAt;
  }

  public void setFavoritedAt(Date favoritedAt) {
    this.favoritedAt = favoritedAt;
  }

  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", idoriginal=" + idoriginal + ", title=" + title + "]";
  }

}
