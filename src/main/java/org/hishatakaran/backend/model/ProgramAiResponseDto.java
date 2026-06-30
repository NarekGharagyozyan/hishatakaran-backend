package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramAiResponseDto {

  private String titleHy;
  private String titleEn;
  private String titleFr;

  private String descriptionHy;
  private String descriptionEn;
  private String descriptionFr;

  private List<ProgramLink> links;

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProgramLink {
    private String linkTitleHy;
    private String linkTitleEn;
    private String linkTitleFr;
    private String link;
  }
}
