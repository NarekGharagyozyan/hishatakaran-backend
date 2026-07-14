package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramAiResponseDto {

  private String title;
  private String description;

  private List<ProgramLinkDto> links;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProgramLinkDto {

    private String linkTitle;
    private String link;
  }

}
