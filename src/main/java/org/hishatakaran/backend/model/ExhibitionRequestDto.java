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
public class ExhibitionRequestDto {

  private String title;
  private String description;
  private String program;
  private List<ProgramLinkRequestDto> links;
  private List<ImageRequestDto> images;
  private List<ExhibitionVideoRequestDto> videos;
  private String cover;
  private String pdf;
}
