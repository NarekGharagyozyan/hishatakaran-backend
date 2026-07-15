package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.NewsResponseDto;

public class NewsMapper {

  public static NewsResponseDto toDto(News news) {

    return new NewsResponseDto(
        news.getId(),
        LanguagesResponseDto.of(
            news.getTitleHy(),
            news.getTitleEn(),
            news.getTitleFr()
        ),
        LanguagesResponseDto.of(
            news.getTextHy(),
            news.getTextEn(),
            news.getTextFr()
        ),
        news.getImages(),
        news.getIsPublished()
    );
  }
}
