package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProgramLinkResponseDto {

  private String titleHy;
  private String titleEn;
  private String titleFr;
  private String url;
}
