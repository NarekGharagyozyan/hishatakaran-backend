package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;

public class DescriptiveCharacteristicMapper {

    public static DescriptiveCharacteristicResponseDto toDto(DescriptiveCharacteristicReference descriptive) {
        return new DescriptiveCharacteristicResponseDto(
            descriptive.getId(),
            LanguagesResponseDto.of(
                descriptive.getOpeningsEntrancesHy(),
                descriptive.getOpeningsEntrancesEn(),
                descriptive.getOpeningsEntrancesFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getConstructionsHy(),
                descriptive.getConstructionsEn(),
                descriptive.getConstructionsFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getRoofHy(),
                descriptive.getRoofEn(),
                descriptive.getRoofFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getTypeHy(),
                descriptive.getTypeEn(),
                descriptive.getTypeFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getColorHy(),
                descriptive.getColorEn(),
                descriptive.getColorFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getTheBuildingMaterialHy(),
                descriptive.getTheBuildingMaterialEn(),
                descriptive.getTheBuildingMaterialFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getImplementationTechniqueHy(),
                descriptive.getImplementationTechniqueEn(),
                descriptive.getImplementationTechniqueFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getStateOfMonumentHy(),
                descriptive.getStateOfMonumentEn(),
                descriptive.getStateOfMonumentFr()
            ),
            LanguagesResponseDto.of(
                descriptive.getValuationHy(),
                descriptive.getValuationEn(),
                descriptive.getValuationFr()
            )
        );
    }
}