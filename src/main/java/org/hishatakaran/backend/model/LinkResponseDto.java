package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LinkResponseDto {

  private LanguagesResponseDto title;
  private String url;
  private String pdf;
}
