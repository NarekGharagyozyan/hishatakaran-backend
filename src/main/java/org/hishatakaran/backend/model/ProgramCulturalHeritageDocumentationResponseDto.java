package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProgramCulturalHeritageDocumentationResponseDto {

  private final LanguagesResponseDto title;
  private final LanguagesResponseDto subtitle;
  private final String background;
}
