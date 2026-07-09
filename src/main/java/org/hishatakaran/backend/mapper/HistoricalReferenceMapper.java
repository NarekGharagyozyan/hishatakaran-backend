package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;

public class HistoricalReferenceMapper {

    public static HistoricalReferenceResponseDto toDto(HistoricalReference historicalReference) {

        return new HistoricalReferenceResponseDto(
            historicalReference.getId(),
            LanguagesResponseDto.of(
                historicalReference.getCulturalAffiliationHy(),
                historicalReference.getCulturalAffiliationEn(),
                historicalReference.getCulturalAffiliationFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getCenturyHy(),
                historicalReference.getCenturyEn(),
                historicalReference.getCenturyFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyHy(),
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyEn(),
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getChronologicalTableOfTheStudHy(),
                historicalReference.getChronologicalTableOfTheStudEn(),
                historicalReference.getChronologicalTableOfTheStudFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getChronologicalTableOfTheMonumentsStudyHy(),
                historicalReference.getChronologicalTableOfTheMonumentsStudyEn(),
                historicalReference.getChronologicalTableOfTheMonumentsStudyFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getAuthorHy(),
                historicalReference.getAuthorEn(),
                historicalReference.getAuthorFr()
            )
        );
    }
}