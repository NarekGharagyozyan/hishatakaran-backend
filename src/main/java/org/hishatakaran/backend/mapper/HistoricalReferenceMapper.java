package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;

public class HistoricalReferenceMapper {

    public static HistoricalReferenceResponseDto toDto(HistoricalReference historicalReference) {

        return new HistoricalReferenceResponseDto(
            historicalReference.getId(),
            historicalReference.getCulturalAffiliationArmenian(),
            historicalReference.getCulturalAffiliationEnglish(),
            historicalReference.getCulturalAffiliationFrench(),
            historicalReference.getCenturyArmenian(),
            historicalReference.getCenturyEnglish(),
            historicalReference.getCenturyFrench(),
            historicalReference.getJustificationOfTheNumberingBasedOnLithographyArmenian(),
            historicalReference.getJustificationOfTheNumberingBasedOnLithographyEnglish(),
            historicalReference.getJustificationOfTheNumberingBasedOnLithographyFrench(),
            historicalReference.getChronologicalTableOfTheStudArmenian(),
            historicalReference.getChronologicalTableOfTheStudEnglish(),
            historicalReference.getChronologicalTableOfTheStudFrench(),
            historicalReference.getAuthorArmenian(),
            historicalReference.getAuthorEnglish(),
            historicalReference.getAuthorFrench()
        );
    }
}