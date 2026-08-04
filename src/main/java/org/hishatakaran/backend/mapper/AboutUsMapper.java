package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.AboutUs;
import org.hishatakaran.backend.model.AboutUsResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;

public class AboutUsMapper {

  public static AboutUsResponseDto toResponseDto(AboutUs aboutUs) {
    return new AboutUsResponseDto(
        LanguagesResponseDto.of(
            aboutUs.getTitleHy(),
            aboutUs.getTitleEn(),
            aboutUs.getTitleFr()
        ),
        LanguagesResponseDto.of(
            aboutUs.getSubtitleHy(),
            aboutUs.getSubtitleEn(),
            aboutUs.getSubtitleFr()
        ),
        LanguagesResponseDto.of(
            aboutUs.getTextHy(),
            aboutUs.getTextEn(),
            aboutUs.getTextFr()
        ),
        aboutUs.getBackground()
    );
  }
}
