package org.hishatakaran.backend.model;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class HistoricalReferenceResponseDto {

    private final UUID id;
    private final String culturalAffiliation;
    private final String century;
    private final String justificationOfTheNumberingBasedOnLithography;
    private final String chronologicalTableOfTheStud;
    private final String author;
}