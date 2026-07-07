package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;

public class DescriptiveCharacteristicMapper {

    public static DescriptiveCharacteristicResponseDto toDto(DescriptiveCharacteristicReference descriptive) {
        return new DescriptiveCharacteristicResponseDto(
            descriptive.getId(),
            new LanguagesResponseDto(
                descriptive.getOpeningsEntrancesHy(),
                descriptive.getOpeningsEntrancesEn(),
                descriptive.getOpeningsEntrancesFr()
            ),
            new LanguagesResponseDto(
                descriptive.getConstructionsHy(),
                descriptive.getConstructionsEn(),
                descriptive.getConstructionsFr()
            ),
            new LanguagesResponseDto(
                descriptive.getRoofHy(),
                descriptive.getRoofEn(),
                descriptive.getRoofFr()
            ),
            new LanguagesResponseDto(
                descriptive.getTypeHy(),
                descriptive.getTypeEn(),
                descriptive.getTypeFr()
            ),
            new LanguagesResponseDto(
                descriptive.getColorHy(),
                descriptive.getColorEn(),
                descriptive.getColorFr()
            ),
            new LanguagesResponseDto(
                descriptive.getTheBuildingMaterialHy(),
                descriptive.getTheBuildingMaterialEn(),
                descriptive.getTheBuildingMaterialFr()
            ),
            new LanguagesResponseDto(
                descriptive.getImplementationTechniqueHy(),
                descriptive.getImplementationTechniqueEn(),
                descriptive.getImplementationTechniqueFr()
            ),
            new LanguagesResponseDto(
                descriptive.getStateOfMonumentHy(),
                descriptive.getStateOfMonumentEn(),
                descriptive.getStateOfMonumentFr()
            ),
            new LanguagesResponseDto(
                descriptive.getValuationHy(),
                descriptive.getValuationEn(),
                descriptive.getValuationFr()
            )
        );
    }
}