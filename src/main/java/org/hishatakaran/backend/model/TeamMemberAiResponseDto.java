package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TeamMemberAiResponseDto {

  private String nameHy;
  private String nameEn;
  private String nameFr;

  private String surnameHy;
  private String surnameEn;
  private String surnameFr;

  private String descriptionHy;
  private String descriptionEn;
  private String descriptionFr;

  private String positionHy;
  private String positionEn;
  private String positionFr;
}
