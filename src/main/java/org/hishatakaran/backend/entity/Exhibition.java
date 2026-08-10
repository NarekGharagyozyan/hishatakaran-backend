package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exhibitions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exhibition extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Boolean isPublished;

  private String titleHy;
  private String titleEn;
  private String titleFr;

  @Column(columnDefinition = "TEXT")
  private String descriptionHy;
  @Column(columnDefinition = "TEXT")
  private String descriptionEn;
  @Column(columnDefinition = "TEXT")
  private String descriptionFr;

  @Column(columnDefinition = "TEXT")
  private String programHy;
  @Column(columnDefinition = "TEXT")
  private String programEn;
  @Column(columnDefinition = "TEXT")
  private String programFr;

  @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ExhibitionImage> images = new ArrayList<>();

  @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ExhibitionVideo> videos = new ArrayList<>();

  private String pdf;
  private String cover;

  @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ExhibitionLink> links = new ArrayList<>();

  public Exhibition(Boolean isPublished, String titleHy, String titleEn, String titleFr, String descriptionHy,
      String descriptionEn, String descriptionFr, List<ExhibitionImage> images, String pdf, String cover, List<ExhibitionLink> links) {
    this.isPublished = isPublished;
    this.titleHy = titleHy;
    this.titleEn = titleEn;
    this.titleFr = titleFr;
    this.descriptionHy = descriptionHy;
    this.descriptionEn = descriptionEn;
    this.descriptionFr = descriptionFr;
    this.images = images;
    this.pdf = pdf;
    this.cover = cover;
    this.links = links;
  }
}
