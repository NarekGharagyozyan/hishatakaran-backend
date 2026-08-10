package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program_type")
  private ProgramType programTypes;

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

  @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProgramImage> images = new ArrayList<>();

  @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProgramEpisode> episodes = new ArrayList<>();

  private String pdf;
  private String cover;

  @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProgramLink> links = new ArrayList<>();

  public Program(Boolean isPublished, String titleHy, String titleEn, String titleFr, String descriptionHy,
      String descriptionEn, String descriptionFr, List<ProgramImage> images, String pdf, String cover, List<ProgramLink> links) {
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
