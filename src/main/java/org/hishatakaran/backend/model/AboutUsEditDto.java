package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AboutUsEditDto {

  private final LanguagesResponseDto title;
  private final LanguagesResponseDto subtitle;
  private final LanguagesResponseDto text;
  private final String background;
}
