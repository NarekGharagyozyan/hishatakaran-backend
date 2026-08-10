package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionTranslationDto {

  private String title;
  private String description;
  private String program;

  private List<LinkTranslationDto> links;
  private List<ExhibitionVideoTranslationDto> videos;
  private List<ExhibitionImageTranslationDto> images;
}
