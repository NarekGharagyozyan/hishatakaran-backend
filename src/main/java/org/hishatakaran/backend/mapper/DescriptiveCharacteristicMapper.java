package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;

public class DescriptiveCharacteristicMapper {

    public static DescriptiveCharacteristicResponseDto toDto(DescriptiveCharacteristicReference descriptive) {
        return new DescriptiveCharacteristicResponseDto(
            descriptive.getId(),
            descriptive.getTypeArmenian(),
            descriptive.getTypeEnglish(),
            descriptive.getTypeFrench(),
            descriptive.getColorArmenian(),
            descriptive.getColorEnglish(),
            descriptive.getColorFrench(),
            descriptive.getTheBuildingMaterialArmenian(),
            descriptive.getTheBuildingMaterialEnglish(),
            descriptive.getTheBuildingMaterialFrench(),
            descriptive.getImplementationTechniqueArmenian(),
            descriptive.getImplementationTechniqueEnglish(),
            descriptive.getImplementationTechniqueFrench(),
            descriptive.getStateOfMonumentArmenian(),
            descriptive.getStateOfMonumentEnglish(),
            descriptive.getStateOfMonumentFrench(),
            descriptive.getValuationArmenian(),
            descriptive.getValuationEnglish(),
            descriptive.getValuationFrench()
        );
    }
}