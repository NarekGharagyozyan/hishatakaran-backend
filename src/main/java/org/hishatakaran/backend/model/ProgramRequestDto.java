package org.hishatakaran.backend.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRequestDto {
  private Long programTypeId;
  private LocalDate date;
  private String title;
  private String description;
  private String program;
  private List<ProgramLinkRequestDto> links;
  private List<ImageRequestDto> images;
  private List<ProgramEpisodeRequestDto> episodes;
  private String cover;
  private String pdf;

  @Override
  public String toString() {
    return "ProgramRequestDto{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", links=" + links +
        '}';
  }
}
