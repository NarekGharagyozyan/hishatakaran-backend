package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;

public class HistoricalReferenceMapper {

    public static HistoricalReferenceResponseDto toDto(HistoricalReference historicalReference) {

        return new HistoricalReferenceResponseDto(
            historicalReference.getId(),
            historicalReference.getCulturalAffiliationHy(),
            historicalReference.getCulturalAffiliationEn(),
            historicalReference.getCulturalAffiliationFr(),
            historicalReference.getCenturyHy(),
            historicalReference.getCenturyEn(),
            historicalReference.getCenturyFr(),
            historicalReference.getJustificationOfTheNumberingBasedOnLithographyHy(),
            historicalReference.getJustificationOfTheNumberingBasedOnLithographyEn(),
            historicalReference.getJustificationOfTheNumberingBasedOnLithographyFr(),
            historicalReference.getChronologicalTableOfTheStudHy(),
            historicalReference.getChronologicalTableOfTheStudEn(),
            historicalReference.getChronologicalTableOfTheStudFr(),
            historicalReference.getAuthorHy(),
            historicalReference.getAuthorEn(),
            historicalReference.getAuthorFr()
        );
    }
}