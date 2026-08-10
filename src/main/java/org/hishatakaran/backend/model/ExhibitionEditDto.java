package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitionEditDto {

  private LanguagesResponseDto title;
  private LanguagesResponseDto description;
  private LanguagesResponseDto program;
  private List<ImageResponseDto> images;
  private List<ExhibitionVideoResponseDto> videos;
  private String pdf;
  private String cover;
  private List<LinkResponseDto> links;
}
