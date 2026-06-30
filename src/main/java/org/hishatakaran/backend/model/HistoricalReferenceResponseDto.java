package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class HistoricalReferenceResponseDto {

    private final Long id;
    private final String culturalAffiliationHy;
    private final String culturalAffiliationEn;
    private final String culturalAffiliationFr;
    private final String centuryHy;
    private final String centuryEn;
    private final String centuryFr;
    private final String justificationOfTheNumberingBasedOnLithographyHy;
    private final String justificationOfTheNumberingBasedOnLithographyEn;
    private final String justificationOfTheNumberingBasedOnLithographyFr;
    private final String chronologicalTableOfTheStudHy;
    private final String chronologicalTableOfTheStudEn;
    private final String chronologicalTableOfTheStudFr;
    private final String authorHy;
    private final String authorEn;
    private final String authorFr;
}