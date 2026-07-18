package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class HistoricalReferenceRequestDto {

  private String culturalAffiliation;
  private String justificationOfTheNumberingBasedOnLithography;
  private String justificationOfTheNumberingBasedOnReliableDocument;
  private String justificationOfTheNumberingBasedOnBibliographicalSources;
  private String justificationOfTheNumberingAccordingIconography;
  private String justificationOfTheNumberingBasedOnEvidence;
  private String chronologicalTableOfTheStud;
  private String chronologicalTableOfTheMonumentsStudy;
  private String author;
  private String briefHistoricalOverview;

  @Override
  public String toString() {
    return "HistoricalReferenceRequestDto{" +
        "culturalAffiliation='" + culturalAffiliation + '\'' +
        ", justificationOfTheNumberingBasedOnLithography='" + justificationOfTheNumberingBasedOnLithography + '\'' +
        ", justificationOfTheNumberingBasedOnReliableDocument='" + justificationOfTheNumberingBasedOnReliableDocument + '\'' +
        ", justificationOfTheNumberingBasedOnBibliographicalSources='" + justificationOfTheNumberingBasedOnBibliographicalSources + '\'' +
        ", justificationOfTheNumberingAccordingIconography='" + justificationOfTheNumberingAccordingIconography + '\'' +
        ", justificationOfTheNumberingBasedOnEvidence='" + justificationOfTheNumberingBasedOnEvidence + '\'' +
        ", chronologicalTableOfTheStud='" + chronologicalTableOfTheStud + '\'' +
        ", chronologicalTableOfTheMonumentsStudy='" + chronologicalTableOfTheMonumentsStudy + '\'' +
        ", author='" + author + '\'' +
        ", briefHistoricalOverview='" + briefHistoricalOverview + '\'' +
        '}';
  }
}
