package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;

public class DescriptiveCharacteristicMapper {

    public static DescriptiveCharacteristicResponseDto toDto(DescriptiveCharacteristicReference descriptive) {
        return new DescriptiveCharacteristicResponseDto(
            descriptive.getId(),
            descriptive.getType(),
            descriptive.getColor().name(),
            descriptive.getTheBuildingMaterial(),
            descriptive.getImplementationTechnique(),
            descriptive.getStateOfMonument().getName(),
            descriptive.getValuation()
        );
    }
}