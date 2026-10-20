package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PageContentResponseDto {
  private final LanguagesResponseDto title;
  private final LanguagesResponseDto description;
  private final String background;
}
