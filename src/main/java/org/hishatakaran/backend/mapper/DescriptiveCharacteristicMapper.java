package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;

public class DescriptiveCharacteristicMapper {

    public static DescriptiveCharacteristicResponseDto toDto(DescriptiveCharacteristicReference descriptive) {
        return new DescriptiveCharacteristicResponseDto(
            descriptive.getId(),
            descriptive.getTypeArmenian(),
            descriptive.getColorArmenian(),
            descriptive.getTheBuildingMaterialArmenian(),
            descriptive.getImplementationTechniqueArmenian(),
            descriptive.getStateOfMonumentArmenian(),
            descriptive.getValuationArmenian()
        );
    }
}