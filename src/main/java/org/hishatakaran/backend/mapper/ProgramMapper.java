package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.model.ProgramLinkResponseDto;
import org.hishatakaran.backend.model.ProgramResponseDto;

public class ProgramMapper {

  public static ProgramResponseDto toResponseDto(Program program) {
    return new ProgramResponseDto(
        program.getId(),
        program.getStatus(),
        program.getTitleHy(),
        program.getTitleEn(),
        program.getTitleFr(),
        program.getDescriptionHy(),
        program.getDescriptionEn(),
        program.getDescriptionFr(),
        program.getImages(),
        program.getPdf(),
        program.getCover(),
        program.getLinks()
            .stream()
            .map(link -> new ProgramLinkResponseDto(
                link.getTitleHy(),
                link.getTitleEn(),
                link.getTitleFr(),
                link.getUrl())
            )
            .toList()
    );
  }
}
