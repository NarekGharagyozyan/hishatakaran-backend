package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class MainPageResponseDto {

  private final LanguagesResponseDto title;
  private final LanguagesResponseDto text;
  private final String background;
}
