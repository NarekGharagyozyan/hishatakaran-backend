package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.ProgramCulturalHeritageDocumentation;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationResponseDto;

public class ProgramCulturalHeritageDocumentationMapper {

  public static ProgramCulturalHeritageDocumentationResponseDto toResponseDto(
      ProgramCulturalHeritageDocumentation culturalHeritages) {
    return new ProgramCulturalHeritageDocumentationResponseDto(
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
