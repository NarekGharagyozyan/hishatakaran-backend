package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class FootnoteResponseDto {

  private final Long id;
  private final Long orderNumber;
  private final LanguagesResponseDto text;
}
