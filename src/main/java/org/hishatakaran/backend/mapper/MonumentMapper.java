package org.hishatakaran.backend.mapper;

import java.util.stream.Collectors;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.model.MonumentResponseDto;

public class MonumentMapper {

    public static MonumentResponseDto toDto(Monument m) {
        if (m == null) return null;

        MonumentResponseDto.MonumentResponseDtoBuilder monumentDtoBuilder = MonumentResponseDto.builder();

        monumentDtoBuilder.id(m.getId());
        monumentDtoBuilder.name(m.getName());
        monumentDtoBuilder.status(m.getStatus().name());
        monumentDtoBuilder.monumentType(m.getMonumentType().name());

        if (m.getRegion() != null) {
            monumentDtoBuilder.region(RegionMapper.toDto(m.getRegion()));
        }

        if (m.getSettlement() != null) {
            monumentDtoBuilder.settlement(SettlementMapper.toDto(m.getSettlement()));
        }

        monumentDtoBuilder.specialName(m.getSpecialName());
        monumentDtoBuilder.anotherNames(m.getAnotherNames());

        monumentDtoBuilder.history(m.getHistory());
        monumentDtoBuilder.originalAffiliation(m.getOriginalAffiliation());
        monumentDtoBuilder.storageUnitName(m.getStorageUnitName());
        monumentDtoBuilder.condition(m.getCondition());

        monumentDtoBuilder.pictures(m.getPictures());

        monumentDtoBuilder.createdAt(m.getCreatedAt());
        monumentDtoBuilder.updatedAt(m.getUpdatedAt());

        monumentDtoBuilder.bibliography(
            m.getBibliography()
                .stream()
                .map(BibliographyMapper::toDto)
                .collect(Collectors.toList())
        );

        monumentDtoBuilder.topographics(
            m.getTopographics()
                .stream()
                .map(TopographicMapper::toDto)
                .collect(Collectors.toList())
        );

        monumentDtoBuilder.historicalReferences(
            m.getHistoricalReferences()
                .stream()
                .map(HistoricalReferenceMapper::toDto)
                .collect(Collectors.toList())
        );

        monumentDtoBuilder.descriptiveCharacteristics(
            m.getDescriptiveCharacteristics()
                .stream()
                .map(DescriptiveCharacteristicMapper::toDto)
                .collect(Collectors.toList())
        );

        return monumentDtoBuilder.build();
    }
}