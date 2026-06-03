package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NewsAiResponseDto {
  private String titleArmenian;

  private String titleEnglish;

  private String titleFrench;

  private String textArmenian;

  private String textEnglish;

  private String textFrench;
}
