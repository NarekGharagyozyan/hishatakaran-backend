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
@Table(name = "program_cultural_heritage_documentation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCulturalHeritageDocumentation {

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

  private String background;


  public ProgramCulturalHeritageDocumentation(String titleHy, String titleEn, String titleFr, String subtitleHy, String subtitleEn,
      String subtitleFr, String background) {
    this.titleHy = titleHy;
    this.titleEn = titleEn;
    this.titleFr = titleFr;
    this.subtitleHy = subtitleHy;
    this.subtitleEn = subtitleEn;
    this.subtitleFr = subtitleFr;
    this.background = background;
  }
}
