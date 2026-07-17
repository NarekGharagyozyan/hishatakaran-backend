package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.SettlementResponseDto;

public class SettlementMapper {

  public static SettlementResponseDto toDto(Settlement settlement) {

    return new SettlementResponseDto(
        settlement.getId(),
        LanguagesResponseDto.of(
            settlement.getNameHy(),
            settlement.getNameEn(),
            settlement.getNameFr()
        ),
        settlement.getRegion().getId()
    );
  }
}
