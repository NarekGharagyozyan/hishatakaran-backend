package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ProgramEpisodeResponseDto {

  private final LanguagesResponseDto title;
  private final String id;
  private final String url;
}
