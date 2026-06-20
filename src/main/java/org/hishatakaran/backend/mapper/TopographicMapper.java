package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.TopographicResponseDto;

public class TopographicMapper {

    public static TopographicResponseDto toDto(Topographic topographic) {

        return new TopographicResponseDto(
            topographic.getId(),
            topographic.getProvinceArmenian(),
            topographic.getAddressArmenian(),
            topographic.getTopographyArmenian(),
            topographic.getDistanceFromResidenceArmenian(),
            topographic.getAltitude(),
            topographic.getHydrographyArmenian(),
            topographic.getDescriptionArmenian()
        );
    }
}