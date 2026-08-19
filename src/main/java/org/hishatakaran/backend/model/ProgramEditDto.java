package org.hishatakaran.backend.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramEditDto {
  private Long programTypeId;
  private LocalDate date;
  private LanguagesResponseDto title;
  private LanguagesResponseDto description;
  private LanguagesResponseDto program;
  private List<ImageResponseDto> images;
  private List<ProgramEpisodeResponseDto> episodes;
  private String pdf;
  private String cover;
  private List<LinkResponseDto> links;
}
