package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.model.NewsResponseDto;

public class NewsMapper {

  public static NewsResponseDto toDto(News news) {

    return new NewsResponseDto(
        news.getId(),
        news.getTitleHy(),
        news.getTitleEn(),
        news.getTitleFr(),
        news.getTextHy(),
        news.getTextEn(),
        news.getTextFr(),
        news.getPictures(),
        news.getStatus().name()
    );
  }
}
