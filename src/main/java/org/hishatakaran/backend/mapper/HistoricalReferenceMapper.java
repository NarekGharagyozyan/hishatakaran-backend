package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;

public class HistoricalReferenceMapper {

    public static HistoricalReferenceResponseDto toDto(HistoricalReference historicalReference) {

        return new HistoricalReferenceResponseDto(
            historicalReference.getId(),
            new LanguagesResponseDto(
                historicalReference.getCulturalAffiliationHy(),
                historicalReference.getCulturalAffiliationEn(),
                historicalReference.getCulturalAffiliationFr()
            ),
            new LanguagesResponseDto(
                historicalReference.getCenturyHy(),
                historicalReference.getCenturyEn(),
                historicalReference.getCenturyFr()
            ),
            new LanguagesResponseDto(
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyHy(),
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyEn(),
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyFr()
            ),
            new LanguagesResponseDto(
                historicalReference.getChronologicalTableOfTheStudHy(),
                historicalReference.getChronologicalTableOfTheStudEn(),
                historicalReference.getChronologicalTableOfTheStudFr()
            ),
            new LanguagesResponseDto(
                historicalReference.getChronologicalTableOfTheMonumentsStudyHy(),
                historicalReference.getChronologicalTableOfTheMonumentsStudyEn(),
                historicalReference.getChronologicalTableOfTheMonumentsStudyFr()
            ),
            new LanguagesResponseDto(
                historicalReference.getAuthorHy(),
                historicalReference.getAuthorEn(),
                historicalReference.getAuthorFr()
            )
        );
    }
}