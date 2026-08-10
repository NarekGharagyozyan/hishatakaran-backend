package org.hishatakaran.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exhibition_videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionVideo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exhibition", nullable = false)
  private Exhibition exhibition;

  private String titleHy;
  private String titleEn;
  private String titleFr;
  private String url;

  public ExhibitionVideo(Exhibition exhibition, String titleHy, String titleEn, String titleFr, String url) {
    this.exhibition = exhibition;
    this.titleHy = titleHy;
    this.titleEn = titleEn;
    this.titleFr = titleFr;
    this.url = url;
  }
}
