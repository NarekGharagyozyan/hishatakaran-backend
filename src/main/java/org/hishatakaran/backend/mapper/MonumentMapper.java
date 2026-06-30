package org.hishatakaran.backend.mapper;

import java.util.stream.Collectors;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.model.BibliographyResponseDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.model.SettlementResponseDto;

public class MonumentMapper {

    public static MonumentResponseDto toDto(Monument m) {
        if (m == null) return null;

        MonumentResponseDto.MonumentResponseDtoBuilder monumentDtoBuilder = MonumentResponseDto.builder();

        monumentDtoBuilder.id(m.getId());
        monumentDtoBuilder.nameHy(m.getNameHy());
        monumentDtoBuilder.nameEn(m.getNameEn());
        monumentDtoBuilder.nameFr(m.getNameFr());
        monumentDtoBuilder.status(m.getStatus().name());
        monumentDtoBuilder.monumentTypeHy(m.getMonumentTypeHy());
        monumentDtoBuilder.monumentTypeEn(m.getMonumentTypeEn());
        monumentDtoBuilder.monumentTypeFr(m.getMonumentTypeFr());

        if (m.getRegion() != null) {
            monumentDtoBuilder.region(RegionMapper.toDto(m.getRegion()));
        }

        if (m.getSettlement() != null) {
            monumentDtoBuilder.settlement(SettlementMapper.toDto(m.getSettlement()));
        }

        monumentDtoBuilder.specialNameHy(m.getSpecialNameHy());
        monumentDtoBuilder.specialNameEn(m.getSpecialNameEn());
        monumentDtoBuilder.specialNameFr(m.getSpecialNameFr());
        monumentDtoBuilder.anotherNamesHy(m.getAnotherNamesHy());
        monumentDtoBuilder.anotherNamesEn(m.getAnotherNamesEn());
        monumentDtoBuilder.anotherNamesFr(m.getAnotherNamesFr());

        monumentDtoBuilder.historyHy(m.getHistoryHy());
        monumentDtoBuilder.historyEn(m.getHistoryEn());
        monumentDtoBuilder.historyFr(m.getHistoryFr());
        monumentDtoBuilder.originalAffiliationHy(m.getOriginalAffiliationHy());
        monumentDtoBuilder.originalAffiliationEn(m.getOriginalAffiliationEn());
        monumentDtoBuilder.originalAffiliationFr(m.getOriginalAffiliationFr());
        monumentDtoBuilder.storageUnitNameHy(m.getStorageUnitNameHy());
        monumentDtoBuilder.storageUnitNameEn(m.getStorageUnitNameEn());
        monumentDtoBuilder.storageUnitNameFr(m.getStorageUnitNameFr());
        monumentDtoBuilder.conditionHy(m.getConditionHy());
        monumentDtoBuilder.conditionEn(m.getConditionEn());
        monumentDtoBuilder.conditionFr(m.getConditionFr());

        monumentDtoBuilder.pictures(m.getPictures());

        monumentDtoBuilder.createdAt(m.getCreatedAt().toEpochSecond());
        monumentDtoBuilder.updatedAt(m.getUpdatedAt().toEpochSecond());

        monumentDtoBuilder.bibliography(
            m.getBibliography()
                .stream()
                .map(bibliography -> new BibliographyResponseDto(bibliography.getId(), bibliography.getTitle(),
                    bibliography.getUrl()))
                .toList()
        );

        monumentDtoBuilder.topographics(TopographicMapper.toDto(m.getTopographics()));
        monumentDtoBuilder.historicalReferences(HistoricalReferenceMapper.toDto(m.getHistoricalReferences()));
        monumentDtoBuilder.descriptiveCharacteristics(DescriptiveCharacteristicMapper.toDto(m.getDescriptiveCharacteristics()));


        return monumentDtoBuilder.build();
    }
}