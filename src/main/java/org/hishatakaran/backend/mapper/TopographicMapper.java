package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.TopographicResponseDto;

public class TopographicMapper {

    public static TopographicResponseDto toDto(Topographic topographic) {

        return new TopographicResponseDto(
            topographic.getId(),
            topographic.getRegionHistory(),
            topographic.getAddress(),
            topographic.getTopography(),
            topographic.getDistanceFromResidence(),
            topographic.getAltitude(),
            topographic.getHydrography(),
            topographic.getDescription()
        );
    }
}