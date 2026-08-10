package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExhibitionVideoResponseDto {

  private final LanguagesResponseDto title;
  private final String id;
  private final String url;
}
