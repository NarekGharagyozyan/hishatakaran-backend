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

  private String nameHy;
  private String nameEn;
  private String nameFr;

  private String surnameHy;
  private String surnameEn;
  private String surnameFr;

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

  public TeamMembers(String nameHy, String nameEn, String nameFr, String surnameHy, String surnameEn, String surnameFr,
      String positionHy, String positionEn, String positionFr, String descriptionHy, String descriptionEn,
      String descriptionFr, String image, String url) {
    this.nameHy = nameHy;
    this.nameEn = nameEn;
    this.nameFr = nameFr;
    this.surnameHy = surnameHy;
    this.surnameEn = surnameEn;
    this.surnameFr = surnameFr;
    this.positionHy = positionHy;
    this.positionEn = positionEn;
    this.positionFr = positionFr;
    this.descriptionHy = descriptionHy;
    this.descriptionEn = descriptionEn;
    this.descriptionFr = descriptionFr;
    this.image = image;
    this.url = url;
  }
}
