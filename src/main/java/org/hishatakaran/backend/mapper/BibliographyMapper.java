package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.model.BibliographyDto;

public class BibliographyMapper {

    public static BibliographyDto toDto(Bibliography b) {
        if (b == null) return null;

        BibliographyDto dto = new BibliographyDto();
        dto.id = b.getId();
        dto.urls = b.getUrls();

        return dto;
    }
}