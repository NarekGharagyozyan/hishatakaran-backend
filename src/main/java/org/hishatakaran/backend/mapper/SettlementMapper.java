package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.model.SettlementResponseDto;

public class SettlementMapper {

  public static SettlementResponseDto toDto(Settlement settlement) {

    return new SettlementResponseDto(
        settlement.getId(),
        settlement.getName(),
        settlement.getMonuments()
            .stream()
            .map(Monument::getId)
            .toList()
    );
  }
}
