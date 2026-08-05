package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.CulturalHeritages;
import org.hishatakaran.backend.model.CulturalHeritagesResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;

public class CulturalHeritagesMapper {

  public static CulturalHeritagesResponseDto toResponseDto(CulturalHeritages culturalHeritages) {
    return new CulturalHeritagesResponseDto(
        LanguagesResponseDto.of(
            culturalHeritages.getTitleHy(),
            culturalHeritages.getTitleEn(),
            culturalHeritages.getTitleFr()
        ),
        LanguagesResponseDto.of(
            culturalHeritages.getSubtitleHy(),
            culturalHeritages.getSubtitleEn(),
            culturalHeritages.getSubtitleFr()
        ),
        culturalHeritages.getBackground()
    );
  }
}
