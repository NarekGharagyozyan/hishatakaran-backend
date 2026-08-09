package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.model.ImageResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.ProgramEpisodeResponseDto;
import org.hishatakaran.backend.model.ProgramLinkResponseDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.ProgramTypeResponseDto;
import org.hishatakaran.backend.service.YouTubeService;

public class ProgramMapper {

  public static ProgramResponseDto toDto(Program program) {
    return new ProgramResponseDto(
        program.getId(),
        program.getIsPublished(),
        program.getProgramTypes() != null ? new ProgramTypeResponseDto(
            program.getProgramTypes().getId(),
            LanguagesResponseDto.of(
                program.getProgramTypes().getNameHy(),
                program.getProgramTypes().getNameEn(),
                program.getProgramTypes().getNameFr()
            )
        ) : null,
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
        program.getProgramHy() != null ? LanguagesResponseDto.of(
            program.getProgramHy(),
            program.getProgramEn(),
            program.getProgramFr()
        ) : null,
        program.getImages()
            .stream()
            .map(image -> new ImageResponseDto(
                image.getId(),
                LanguagesResponseDto.of(
                    image.getCaptionHy(),
                    image.getCaptionEn(),
                    image.getCaptionFr()
                ),
                image.getUrl()
            ))
            .toList(),
        program.getEpisodes()
            .stream()
            .map(episode -> new ProgramEpisodeResponseDto(
                LanguagesResponseDto.of(
                    episode.getTitleHy(),
                    episode.getTitleEn(),
                    episode.getTitleFr()
                ),
                YouTubeService.extractVideoId(episode.getUrl()),
                episode.getUrl()
            ))
            .toList(),
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
