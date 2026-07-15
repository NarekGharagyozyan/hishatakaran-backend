package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.ProgramLinkResponseDto;
import org.hishatakaran.backend.model.ProgramResponseDto;

public class ProgramMapper {

  public static ProgramResponseDto toResponseDto(Program program) {
    return new ProgramResponseDto(
        program.getId(),
        program.getIsPublished(),
        program.getTitleHy() != null ? LanguagesResponseDto.of(
            program.getTitleHy(),
            program.getTitleEn(),
            program.getTitleFr()
        ) : null,
        program.getDescriptionHy() != null ? LanguagesResponseDto.of(
            program.getDescriptionHy(),
            program.getDescriptionEn(),
            program.getDescriptionFr()
        ) : null,
        program.getImages(),
        program.getPdf(),
        program.getCover(),
        program.getLinks() != null ? program.getLinks()
            .stream()
            .map(link -> new ProgramLinkResponseDto(
                LanguagesResponseDto.of(
                    link.getTitleHy(),
                    link.getTitleEn(),
                    link.getTitleFr()
                ),
                link.getUrl())
            )
            .toList() : null
    );
  }
}
