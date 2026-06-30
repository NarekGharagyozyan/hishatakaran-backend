package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRequestDto {
  private String title;
  private String description;
  private List<ProgramLinkRequestDto> links;

  @Override
  public String toString() {
    return "ProgramRequestDto{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", links=" + links +
        '}';
  }
}
