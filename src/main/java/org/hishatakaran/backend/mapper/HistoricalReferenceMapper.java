package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.model.HistoricalReferenceDto;

public class HistoricalReferenceMapper {

    public static HistoricalReferenceDto toDto(HistoricalReference h) {
        if (h == null) return null;

        HistoricalReferenceDto dto = new HistoricalReferenceDto();
        dto.id = h.getId();
        dto.culturalAffiliation = h.getCulturalAffiliation();
        dto.century = h.getCentury();
        dto.author = h.getAuthor();

        return dto;
    }
}