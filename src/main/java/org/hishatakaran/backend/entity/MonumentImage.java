package org.hishatakaran.backend.entity;

import jakarta.persistence.Column;
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
@Table(name = "monument_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonumentImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String url;

  @Column(columnDefinition = "TEXT")
  private String captionHy;
  @Column(columnDefinition = "TEXT")
  private String captionEn;
  @Column(columnDefinition = "TEXT")
  private String captionFr;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "monument_id")
  private Monument monument;

  public MonumentImage(String url, String captionHy, String captionEn, String captionFr, Monument monument) {
    this.url = url;
    this.captionHy = captionHy;
    this.captionEn = captionEn;
    this.captionFr = captionFr;
    this.monument = monument;
  }
}
