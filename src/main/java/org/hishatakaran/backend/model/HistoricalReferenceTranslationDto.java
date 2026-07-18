package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricalReferenceTranslationDto {


    private String culturalAffiliation;
    private String justificationOfTheNumberingBasedOnReliableDocument;
    private String justificationOfTheNumberingBasedOnBibliographicalSources;
    private String justificationOfTheNumberingAccordingIconography;
    private String justificationOfTheNumberingBasedOnEvidence;
    private String justificationOfTheNumberingBasedOnLithography;
    private String chronologicalTableOfTheStud;
    private String chronologicalTableOfTheMonumentsStudy;
    private String author;
    private String briefHistoricalOverview;
}