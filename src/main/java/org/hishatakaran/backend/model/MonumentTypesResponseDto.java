package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class MonumentTypesResponseDto {

  private final Long id;
  private final LanguagesResponseDto types;
}
