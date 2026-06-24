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
    private final String culturalAffiliationArmenian;
    private final String culturalAffiliationEnglish;
    private final String culturalAffiliationFrench;
    private final String centuryArmenian;
    private final String centuryEnglish;
    private final String centuryFrench;
    private final String justificationOfTheNumberingBasedOnLithographyArmenian;
    private final String justificationOfTheNumberingBasedOnLithographyEnglish;
    private final String justificationOfTheNumberingBasedOnLithographyFrench;
    private final String chronologicalTableOfTheStudArmenian;
    private final String chronologicalTableOfTheStudEnglish;
    private final String chronologicalTableOfTheStudFrench;
    private final String authorArmenian;
    private final String authorEnglish;
    private final String authorFrench;
}