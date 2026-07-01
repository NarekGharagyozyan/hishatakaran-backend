package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.model.BibliographyResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.LanguagesWithListResponseDto;
import org.hishatakaran.backend.model.MonumentResponseDto;

public class MonumentMapper {

    public static MonumentResponseDto toDto(Monument m) {
        if (m == null) return null;

        MonumentResponseDto.MonumentResponseDtoBuilder monumentDtoBuilder = MonumentResponseDto.builder();

        monumentDtoBuilder.id(m.getId());
        monumentDtoBuilder.name(new LanguagesResponseDto(
            m.getNameHy(),
            m.getNameEn(),
            m.getNameFr()
        ));
        monumentDtoBuilder.status(m.getStatus().name());
        monumentDtoBuilder.monumentType(new LanguagesResponseDto(
            m.getMonumentTypeHy(),
            m.getMonumentTypeEn(),
            m.getMonumentTypeFr()
        ));

        if (m.getRegion() != null) {
            monumentDtoBuilder.region(RegionMapper.toDto(m.getRegion()));
        }

        if (m.getSettlement() != null) {
            monumentDtoBuilder.settlement(SettlementMapper.toDto(m.getSettlement()));
        }

        monumentDtoBuilder.specialName(new LanguagesResponseDto(
            m.getSpecialNameHy(),
            m.getSpecialNameEn(),
            m.getSpecialNameFr()
        ));
        monumentDtoBuilder.anotherNames(new LanguagesWithListResponseDto(
            m.getAnotherNamesHy(),
            m.getAnotherNamesEn(),
            m.getAnotherNamesFr()
        ));

        monumentDtoBuilder.history(new LanguagesResponseDto(
            m.getHistoryHy(),
            m.getHistoryEn(),
            m.getHistoryFr()
        ));
        monumentDtoBuilder.originalAffiliation(new LanguagesResponseDto(
            m.getOriginalAffiliationHy(),
            m.getOriginalAffiliationEn(),
            m.getOriginalAffiliationFr()
        ));
        monumentDtoBuilder.storageUnitName(new LanguagesResponseDto(
            m.getStorageUnitNameHy(),
            m.getStorageUnitNameEn(),
            m.getStorageUnitNameFr()
        ));
        monumentDtoBuilder.condition(new LanguagesResponseDto(
            m.getConditionHy(),
            m.getConditionEn(),
            m.getConditionFr()
        ));

        monumentDtoBuilder.images(m.getImages());

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