package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.TopographicDto;

public class TopographicMapper {

    public static TopographicDto toDto(Topographic t) {
        if (t == null) return null;

        TopographicDto dto = new TopographicDto();
        dto.id = t.getId();
        dto.address = t.getAddress();
        dto.topography = t.getTopography();

        return dto;
    }
}