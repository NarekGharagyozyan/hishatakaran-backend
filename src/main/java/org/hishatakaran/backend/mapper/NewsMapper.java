package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.model.NewsResponseDto;

public class NewsMapper {

  public static NewsResponseDto toDto(News news) {

    return new NewsResponseDto(
        news.getId(),
        news.getTitleArmenian(),
        news.getTitleEnglish(),
        news.getTitleFrench(),
        news.getTextArmenian(),
        news.getTextEnglish(),
        news.getTextFrench(),
        news.getPictures(),
        news.getStatus().name()
    );
  }
}
