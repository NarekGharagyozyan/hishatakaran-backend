package org.hishatakaran.backend.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ProgramResponseDto {

  private final Long id;
  private final Boolean isPublished;

  private final LocalDate date;

  private final ProgramTypeResponseDto programType;

  private final LanguagesResponseDto title;
  private final LanguagesResponseDto description;
  private final LanguagesResponseDto program;

  private final List<ImageResponseDto> images;
  private final List<ProgramEpisodeResponseDto> episodes;

  private final String pdf;
  private final String cover;

  private final List<LinkResponseDto> links;
}
