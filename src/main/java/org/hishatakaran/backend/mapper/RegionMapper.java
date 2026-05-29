package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.model.RegionResponseDto;

public class RegionMapper {

  public static RegionResponseDto toDto(Region region) {

    return new RegionResponseDto(
        region.getId(),
        region.getName(),
        region.getSettlements()
            .stream()
            .map(Settlement::getId)
            .toList(),
        region.getMonuments()
            .stream()
            .map(Monument::getId)
            .toList()
    );
  }
}
