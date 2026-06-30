package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.model.RegionResponseDto;

public class RegionMapper {

  public static RegionResponseDto toDto(Region region) {

    return new RegionResponseDto(
        region.getId(),
        region.getNameHy(),
        region.getNameEn(),
        region.getNameFr()
    );
  }
}
