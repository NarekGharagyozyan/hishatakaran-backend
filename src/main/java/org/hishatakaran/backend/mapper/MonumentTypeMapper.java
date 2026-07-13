package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.MonumentTypes;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MonumentTypesResponseDto;

public class MonumentTypeMapper {

  public static MonumentTypesResponseDto toDto(MonumentTypes monumentTypes) {

    return new MonumentTypesResponseDto(
        monumentTypes.getId(),
        new LanguagesResponseDto(
            monumentTypes.getNameHy(),
            monumentTypes.getNameEn(),
            monumentTypes.getNameFr()
        )
    );
  }
}
