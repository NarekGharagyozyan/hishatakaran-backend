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
    private final LanguagesResponseDto century;
    private final LanguagesResponseDto justificationOfTheNumberingBasedOnLithography;
    private final LanguagesResponseDto chronologicalTableOfTheStud;
    private final LanguagesResponseDto author;
}