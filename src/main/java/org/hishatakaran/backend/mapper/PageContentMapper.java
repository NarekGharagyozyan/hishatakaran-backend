package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.PageContent;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.PageContentResponseDto;

public class PageContentMapper {

  public static PageContentResponseDto toResponseDto(PageContent pageContent) {
    return new PageContentResponseDto(
        LanguagesResponseDto.of(
            pageContent.getTitleHy(),
            pageContent.getTitleEn(),
            pageContent.getTitleFr()
        ),
        LanguagesResponseDto.of(
            pageContent.getDescriptionHy(),
            pageContent.getDescriptionEn(),
            pageContent.getDescriptionFr()
        ),
        pageContent.getBackground()
    );
  }
}
