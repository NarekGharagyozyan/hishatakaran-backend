package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ProgramResponseDto {

  private final Long id;
  private final Status status;

  private final LanguagesResponseDto title;
  private final LanguagesResponseDto description;

  private final List<String> images;

  private final String pdf;
  private final String cover;

  private final List<ProgramLinkResponseDto> links;
}
