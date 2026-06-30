package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;

public class DescriptiveCharacteristicMapper {

    public static DescriptiveCharacteristicResponseDto toDto(DescriptiveCharacteristicReference descriptive) {
        return new DescriptiveCharacteristicResponseDto(
            descriptive.getId(),
            descriptive.getTypeHy(),
            descriptive.getTypeEn(),
            descriptive.getTypeFr(),
            descriptive.getColorHy(),
            descriptive.getColorEn(),
            descriptive.getColorFr(),
            descriptive.getTheBuildingMaterialHy(),
            descriptive.getTheBuildingMaterialEn(),
            descriptive.getTheBuildingMaterialFr(),
            descriptive.getImplementationTechniqueHy(),
            descriptive.getImplementationTechniqueEn(),
            descriptive.getImplementationTechniqueFr(),
            descriptive.getStateOfMonumentHy(),
            descriptive.getStateOfMonumentEn(),
            descriptive.getStateOfMonumentFr(),
            descriptive.getValuationHy(),
            descriptive.getValuationEn(),
            descriptive.getValuationFr()
        );
    }
}