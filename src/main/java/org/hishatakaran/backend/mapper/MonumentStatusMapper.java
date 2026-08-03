package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.MonumentStatus;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MonumentStatusResponseDto;

public class MonumentStatusMapper {

    public static MonumentStatusResponseDto toDto(MonumentStatus monumentStatus)
    {
      if (monumentStatus == null)
        return null;

      return new MonumentStatusResponseDto(
          monumentStatus.getId(),
          LanguagesResponseDto.of(
              monumentStatus.getNameHy(),
              monumentStatus.getNameEn(),
              monumentStatus.getNameFr()
          )
      );
    }
}
