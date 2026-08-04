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
@Table(name = "about_us")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AboutUs {

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
  private String subtitleHy;
  @Column(columnDefinition = "TEXT")
  private String subtitleEn;
  @Column(columnDefinition = "TEXT")
  private String subtitleFr;

  @Column(columnDefinition = "TEXT")
  private String textHy;
  @Column(columnDefinition = "TEXT")
  private String textEn;
  @Column(columnDefinition = "TEXT")
  private String textFr;

  private String background;

  public AboutUs(String titleHy, String titleEn, String titleFr, String subtitleHy, String subtitleEn,
      String subtitleFr,
      String textHy, String textEn, String textFr, String background) {
    this.titleHy = titleHy;
    this.titleEn = titleEn;
    this.titleFr = titleFr;
    this.subtitleHy = subtitleHy;
    this.subtitleEn = subtitleEn;
    this.subtitleFr = subtitleFr;
    this.textHy = textHy;
    this.textEn = textEn;
    this.textFr = textFr;
    this.background = background;
  }
}
