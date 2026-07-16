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
@Table(name = "team_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMembers {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullNameHy;
  private String fullNameEn;
  private String fullNameFr;

  @Column(columnDefinition = "TEXT")
  private String positionHy;
  @Column(columnDefinition = "TEXT")
  private String positionEn;
  @Column(columnDefinition = "TEXT")
  private String positionFr;

  @Column(columnDefinition = "TEXT")
  private String descriptionHy;
  @Column(columnDefinition = "TEXT")
  private String descriptionEn;
  @Column(columnDefinition = "TEXT")
  private String descriptionFr;

  private String image;

  private String url;

  private String signature;

  public TeamMembers(String fullNameHy, String fullNameEn, String fullNameFr,
      String positionHy, String positionEn, String positionFr, String descriptionHy, String descriptionEn,
      String descriptionFr, String image, String signature, String url) {
    this.fullNameHy = fullNameHy;
    this.fullNameEn = fullNameEn;
    this.fullNameFr = fullNameFr;
    this.positionHy = positionHy;
    this.positionEn = positionEn;
    this.positionFr = positionFr;
    this.descriptionHy = descriptionHy;
    this.descriptionEn = descriptionEn;
    this.descriptionFr = descriptionFr;
    this.image = image;
    this.signature = signature;
    this.url = url;
  }
}
