package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicDto;

public class DescriptiveCharacteristicMapper {

    public static DescriptiveCharacteristicDto toDto(DescriptiveCharacteristicReference d) {
        if (d == null) return null;

        DescriptiveCharacteristicDto dto = new DescriptiveCharacteristicDto();
        dto.id = d.getId();
        dto.type = d.getType();
        dto.color = d.getColor().name();
        dto.buildingMaterial = d.getTheBuildingMaterial();

        return dto;
    }
}