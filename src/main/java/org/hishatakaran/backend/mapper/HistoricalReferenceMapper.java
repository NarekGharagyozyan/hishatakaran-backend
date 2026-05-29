package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;

public class HistoricalReferenceMapper {

    public static HistoricalReferenceResponseDto toDto(HistoricalReference historicalReference) {

        return new HistoricalReferenceResponseDto(
            historicalReference.getId(),
            historicalReference.getCulturalAffiliation(),
            historicalReference.getCentury(),
            historicalReference.getJustificationOfTheNumberingBasedOnLithography(),
            historicalReference.getChronologicalTableOfTheStud(),
            historicalReference.getAuthor()
        );
    }
}