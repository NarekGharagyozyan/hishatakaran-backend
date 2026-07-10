package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Program extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Boolean isPublished;

  private String titleHy;
  private String titleEn;
  private String titleFr;

  private String descriptionHy;
  private String descriptionEn;
  private String descriptionFr;

  @ElementCollection
  @CollectionTable(name = "program_images", joinColumns = @JoinColumn(name = "program_id"))
  @Column(name = "image_urls")
  private List<String> images = new ArrayList<>();

  private String pdf;
  private String cover;

  @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProgramLink> links = new ArrayList<>();

  public Program(Boolean isPublished, String titleHy, String titleEn, String titleFr, String descriptionHy,
      String descriptionEn, String descriptionFr, List<String> images, String pdf, String cover, List<ProgramLink> links) {
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
