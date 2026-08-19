package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Exhibition;
import org.hishatakaran.backend.model.ExhibitionResponseDto;
import org.hishatakaran.backend.model.ExhibitionVideoResponseDto;
import org.hishatakaran.backend.model.ImageResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.LinkResponseDto;
import org.hishatakaran.backend.service.YouTubeService;

public class ExhibitionMapper {

  public static ExhibitionResponseDto toDto(Exhibition exhibition) {
    return new ExhibitionResponseDto(
        exhibition.getId(),
        exhibition.getIsPublished(),
        exhibition.getDate(),
        exhibition.getTitleHy() != null ? LanguagesResponseDto.of(
            exhibition.getTitleHy(),
            exhibition.getTitleEn(),
            exhibition.getTitleFr()
        ) : null,
        exhibition.getDescriptionHy() != null ? LanguagesResponseDto.of(
            exhibition.getDescriptionHy(),
            exhibition.getDescriptionEn(),
            exhibition.getDescriptionFr()
        ) : null,
        exhibition.getProgramHy() != null ? LanguagesResponseDto.of(
            exhibition.getProgramHy(),
            exhibition.getProgramEn(),
            exhibition.getProgramFr()
        ) : null,
        exhibition.getImages()
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
        exhibition.getVideos()
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
        exhibition.getPdf(),
        exhibition.getCover(),
        exhibition.getLinks() != null ? exhibition.getLinks()
            .stream()
            .map(link -> new LinkResponseDto(
                LanguagesResponseDto.of(
                    link.getTitleHy(),
                    link.getTitleEn(),
                    link.getTitleFr()
                ),
                link.getUrl(),
                link.getPdf())
            )
            .toList() : null
    );
  }
}
