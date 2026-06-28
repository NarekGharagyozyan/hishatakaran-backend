package org.hishatakaran.backend.mapper;

import java.util.stream.Collectors;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.model.SettlementResponseDto;

public class MonumentMapper {

    public static MonumentResponseDto toDto(Monument m) {
        if (m == null) return null;

        MonumentResponseDto.MonumentResponseDtoBuilder monumentDtoBuilder = MonumentResponseDto.builder();

        monumentDtoBuilder.id(m.getId());
        monumentDtoBuilder.nameArmenian(m.getNameArmenian());
        monumentDtoBuilder.nameEnglish(m.getNameEnglish());
        monumentDtoBuilder.nameFrench(m.getNameFrench());
        monumentDtoBuilder.status(m.getStatus().name());
        monumentDtoBuilder.monumentTypeArmenian(m.getMonumentTypeArmenian());
        monumentDtoBuilder.monumentTypeEnglish(m.getMonumentTypeEnglish());
        monumentDtoBuilder.monumentTypeFrench(m.getMonumentTypeFrench());

        if (m.getRegion() != null) {
            monumentDtoBuilder.region(RegionMapper.toDto(m.getRegion()));
        }

        if (m.getSettlement() != null) {
            monumentDtoBuilder.settlement(SettlementMapper.toDto(m.getSettlement()));
        }

        monumentDtoBuilder.specialNameArmenian(m.getSpecialNameArmenian());
        monumentDtoBuilder.specialNameEnglish(m.getSpecialNameEnglish());
        monumentDtoBuilder.specialNameFrench(m.getSpecialNameFrench());
        monumentDtoBuilder.anotherNamesArmenian(m.getAnotherNamesArmenian());
        monumentDtoBuilder.anotherNamesEnglish(m.getAnotherNamesEnglish());
        monumentDtoBuilder.anotherNamesFrench(m.getAnotherNamesFrench());

        monumentDtoBuilder.historyArmenian(m.getHistoryArmenian());
        monumentDtoBuilder.historyEnglish(m.getHistoryEnglish());
        monumentDtoBuilder.historyFrench(m.getHistoryFrench());
        monumentDtoBuilder.originalAffiliationArmenian(m.getOriginalAffiliationArmenian());
        monumentDtoBuilder.originalAffiliationEnglish(m.getOriginalAffiliationEnglish());
        monumentDtoBuilder.originalAffiliationFrench(m.getOriginalAffiliationFrench());
        monumentDtoBuilder.storageUnitNameArmenian(m.getStorageUnitNameArmenian());
        monumentDtoBuilder.storageUnitNameEnglish(m.getStorageUnitNameEnglish());
        monumentDtoBuilder.storageUnitNameFrench(m.getStorageUnitNameFrench());
        monumentDtoBuilder.conditionArmenian(m.getConditionArmenian());
        monumentDtoBuilder.conditionEnglish(m.getConditionEnglish());
        monumentDtoBuilder.conditionFrench(m.getConditionFrench());

        monumentDtoBuilder.pictures(m.getPictures());

        monumentDtoBuilder.createdAt(m.getCreatedAt());
        monumentDtoBuilder.updatedAt(m.getUpdatedAt());

        monumentDtoBuilder.bibliography(m.getBibliography());

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