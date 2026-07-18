package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class HistoricalReferenceResponseDto {

    private final Long id;
    private final LanguagesResponseDto culturalAffiliation;
    private final LanguagesResponseDto justificationOfTheNumberingBasedOnLithography;
    private final LanguagesResponseDto justificationOfTheNumberingBasedOnReliableDocument;
    private final LanguagesResponseDto justificationOfTheNumberingBasedOnBibliographicalSources;
    private final LanguagesResponseDto justificationOfTheNumberingAccordingIconography;
    private final LanguagesResponseDto justificationOfTheNumberingBasedOnEvidence;
    private final LanguagesResponseDto chronologicalTableOfTheStud;
    private final LanguagesResponseDto chronologicalTableOfTheMonumentsStudy;
    private final LanguagesResponseDto author;
    private final LanguagesResponseDto briefHistoricalOverview;
}