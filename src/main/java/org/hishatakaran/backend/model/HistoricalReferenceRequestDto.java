package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class HistoricalReferenceRequestDto {

  private String culturalAffiliation;
  private String century;
  private String justificationOfTheNumberingBasedOnLithography;
  private String chronologicalTableOfTheStud;
  private String author;

  @Override
  public String toString() {
    return "HistoricalReferenceRequestDto{" +
        "culturalAffiliation='" + culturalAffiliation + '\'' +
        ", century='" + century + '\'' +
        ", justificationOfTheNumberingBasedOnLithography='" + justificationOfTheNumberingBasedOnLithography + '\'' +
        ", chronologicalTableOfTheStud='" + chronologicalTableOfTheStud + '\'' +
        ", author='" + author + '\'' +
        '}';
  }
}
