package org.hishatakaran.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "main_page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainPage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String titleHy;
  @Column(columnDefinition = "TEXT")
  private String titleEn;
  @Column(columnDefinition = "TEXT")
  private String titleFr;

  @Column(columnDefinition = "TEXT")
  private String textHy;
  @Column(columnDefinition = "TEXT")
  private String textEn;
  @Column(columnDefinition = "TEXT")
  private String textFr;

  private String background;

  public MainPage(String titleHy, String titleEn, String titleFr, String textHy, String textEn, String textFr,
      String background) {
    this.titleHy = titleHy;
    this.titleEn = titleEn;
    this.titleFr = titleFr;
    this.textHy = textHy;
    this.textEn = textEn;
    this.textFr = textFr;
    this.background = background;
  }
}
