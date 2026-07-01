package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LibraryAiResponseDto {

  private String titleHy;
  private String titleEn;
  private String titleFr;
  private String descriptionHy;
  private String descriptionEn;
  private String descriptionFr;
  private String copyrightTextHy;
  private String copyrightTextEn;
  private String copyrightTextFr;
  private String authorsHy;
  private String authorsEn;
  private String authorsFr;
}
