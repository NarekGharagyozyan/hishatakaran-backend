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
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyHy(),
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyEn(),
                historicalReference.getJustificationOfTheNumberingBasedOnLithographyFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getJustificationOfTheNumberingBasedOnReliableDocumentHy(),
                historicalReference.getJustificationOfTheNumberingBasedOnReliableDocumentEn(),
                historicalReference.getJustificationOfTheNumberingBasedOnReliableDocumentFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getJustificationOfTheNumberingBasedOnBibliographicalSourcesHy(),
                historicalReference.getJustificationOfTheNumberingBasedOnBibliographicalSourcesEn(),
                historicalReference.getJustificationOfTheNumberingBasedOnBibliographicalSourcesFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getJustificationOfTheNumberingAccordingIconographyHy(),
                historicalReference.getJustificationOfTheNumberingAccordingIconographyEn(),
                historicalReference.getJustificationOfTheNumberingAccordingIconographyFr()
            ),
            LanguagesResponseDto.of(
                historicalReference.getJustificationOfTheNumberingBasedOnEvidenceHy(),
                historicalReference.getJustificationOfTheNumberingBasedOnEvidenceEn(),
                historicalReference.getJustificationOfTheNumberingBasedOnEvidenceFr()
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
            ),
            LanguagesResponseDto.of(
                historicalReference.getBriefHistoricalOverviewHy(),
                historicalReference.getBriefHistoricalOverviewEn(),
                historicalReference.getBriefHistoricalOverviewFr()
            )
        );
    }
}