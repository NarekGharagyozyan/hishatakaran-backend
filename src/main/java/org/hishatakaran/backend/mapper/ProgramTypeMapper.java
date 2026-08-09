package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.ProgramType;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.ProgramTypeResponseDto;

public class ProgramTypeMapper {

  public static ProgramTypeResponseDto toDto(ProgramType programType)
  {
    if (programType == null)
      return null;

    return new ProgramTypeResponseDto(
        programType.getId(),
        LanguagesResponseDto.of(
            programType.getNameHy(),
            programType.getNameEn(),
            programType.getNameFr()
        )
    );
  }
}
