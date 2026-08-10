package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Exhibition;
import org.hishatakaran.backend.model.ExhibitionResponseDto;
import org.hishatakaran.backend.model.ExhibitionVideoResponseDto;
import org.hishatakaran.backend.model.ImageResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.LinkResponseDto;
import org.hishatakaran.backend.service.YouTubeService;

public class ExhibitionMapper {

  public static ExhibitionResponseDto toDto(Exhibition program) {
    return new ExhibitionResponseDto(
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
        program.getVideos()
            .stream()
            .map(video -> new ExhibitionVideoResponseDto(
                LanguagesResponseDto.of(
                    video.getTitleHy(),
                    video.getTitleEn(),
                    video.getTitleFr()
                ),
                YouTubeService.extractVideoId(video.getUrl()),
                video.getUrl()
            ))
            .toList(),
        program.getPdf(),
        program.getCover(),
        program.getLinks() != null ? program.getLinks()
            .stream()
            .map(link -> new LinkResponseDto(
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
