package org.hishatakaran.backend.mapper;

import java.util.stream.Collectors;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.model.MonumentDto;

public class MonumentMapper {

    public static MonumentDto toDto(Monument m) {
        if (m == null) return null;

        MonumentDto dto = new MonumentDto();

        dto.id = m.getId();
        dto.name = m.getName();
        dto.status = m.getStatus().name();
        dto.monumentType = m.getMonumentType().name();

        if (m.getRegion() != null) {
            dto.regionId = m.getRegion().getId();
            dto.regionName = m.getRegion().getName();
        }

        if (m.getSettlement() != null) {
            dto.settlementId = m.getSettlement().getId();
        }

        dto.specialName = m.getSpecialName();
        dto.anotherNames = m.getAnotherNames();

        dto.history = m.getHistory();
        dto.originalAffiliation = m.getOriginalAffiliation();
        dto.storageUnitName = m.getStorageUnitName();
        dto.condition = m.getCondition();

        dto.pictures = m.getPictures();

        dto.createdAt = m.getCreatedAt();
        dto.updatedAt = m.getUpdatedAt();

        dto.bibliography = m.getBibliography()
                .stream()
                .map(BibliographyMapper::toDto)
                .collect(Collectors.toList());

        dto.topographics = m.getTopographics()
                .stream()
                .map(TopographicMapper::toDto)
                .collect(Collectors.toList());

        dto.historicalReferences = m.getHistoricalReferences()
                .stream()
                .map(HistoricalReferenceMapper::toDto)
                .collect(Collectors.toList());

        dto.descriptiveCharacteristics = m.getDescriptiveCharacteristics()
                .stream()
                .map(DescriptiveCharacteristicMapper::toDto)
                .collect(Collectors.toList());

        return dto;
    }
}