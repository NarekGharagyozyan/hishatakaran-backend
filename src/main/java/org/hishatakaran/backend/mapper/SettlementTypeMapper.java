package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.SettlementType;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.SettlementTypeResponseDto;

public class SettlementTypeMapper {

  public static SettlementTypeResponseDto toDto(SettlementType settlementType) {

    if (settlementType == null) {
      return null;
    }

    return new SettlementTypeResponseDto(
        settlementType.getId(),
        LanguagesResponseDto.of(
            settlementType.getNameHy(),
            settlementType.getNameEn(),
            settlementType.getNameFr()
        )
    );
  }
}
