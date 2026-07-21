package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ImageResponseDto {

  private final Long id;
  private final LanguagesResponseDto caption;
  private final String url;
}
