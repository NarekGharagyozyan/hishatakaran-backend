package org.hishatakaran.backend.entity;

import org.hishatakaran.backend.model.PageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The content of one {@link PageType} page: one row per page, like main_page. */
@Entity
@Table(name = "page_contents")
@Getter
@Setter
@NoArgsConstructor
public class PageContent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private PageType page;

  @Column(columnDefinition = "TEXT")
  private String titleHy;

  @Column(columnDefinition = "TEXT")
  private String titleEn;

  @Column(columnDefinition = "TEXT")
  private String titleFr;

  @Column(columnDefinition = "TEXT")
  private String descriptionHy;

  @Column(columnDefinition = "TEXT")
  private String descriptionEn;

  @Column(columnDefinition = "TEXT")
  private String descriptionFr;

  private String background;

  public PageContent(PageType page) {
    this.page = page;
  }
}
