package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProgramTypeResponseDto {
  private final Long id;
  private final LanguagesResponseDto name;
}
