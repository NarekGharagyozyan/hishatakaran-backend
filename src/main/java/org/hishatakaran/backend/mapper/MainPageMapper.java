package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.MainPage;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MainPageResponseDto;

public class MainPageMapper {

  public static MainPageResponseDto toResponseDto(MainPage mainPage) {
    return new MainPageResponseDto(
        LanguagesResponseDto.of(
            mainPage.getTitleHy(),
            mainPage.getTitleEn(),
            mainPage.getTitleFr()
        ),
        LanguagesResponseDto.of(
            mainPage.getTextHy(),
            mainPage.getTextEn(),
            mainPage.getTextFr()
        ),
        mainPage.getBackground()
    );
  }
}
