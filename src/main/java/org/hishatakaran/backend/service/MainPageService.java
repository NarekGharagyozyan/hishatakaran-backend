package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.MainPage;
import org.hishatakaran.backend.entity.MonumentStatus;
import org.hishatakaran.backend.mapper.MainPageMapper;
import org.hishatakaran.backend.model.MainPageEditDto;
import org.hishatakaran.backend.model.MainPageRequestDto;
import org.hishatakaran.backend.model.MainPageResponseDto;
import org.hishatakaran.backend.model.MainPageTranslationDto;
import org.hishatakaran.backend.model.MediaCountDto;
import org.hishatakaran.backend.model.MonumentCountDto;
import org.hishatakaran.backend.model.QuantityResponseDto;
import org.hishatakaran.backend.model.SettlementsCountDto;
import org.hishatakaran.backend.repository.MainPageRepository;
import org.hishatakaran.backend.repository.MonumentImageRepository;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.MonumentStatusRepository;
import org.hishatakaran.backend.repository.MonumentVideoRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainPageService {

  private final MainPageRepository mainPageRepository;
  private final MonumentRepository monumentRepository;
  private final MonumentImageRepository monumentImageRepository;
  private final MonumentVideoRepository monumentVideoRepository;
  private final MonumentStatusRepository monumentStatusRepository;
  private final SettlementRepository settlementRepository;
  private final GeminiService geminiService;


  public MainPageResponseDto createMainPage(MainPageRequestDto mainPageRequestDto) {
    List<MainPage> all = mainPageRepository.findAll();
    if (all.size() > 1) {
      throw new RuntimeException("Something went wrong");
    }

    MainPageTranslationDto translation;

    try {
      translation = geminiService.translateMainPage(mainPageRequestDto);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    MainPage mainPage;

    if (all.isEmpty()) {
      mainPage = new MainPage();
    }
    else {
      mainPage = all.getFirst();
    }

    mainPage.setTitleHy(translation.getTitleHy());
    mainPage.setTitleEn(translation.getTitleEn());
    mainPage.setTitleFr(translation.getTitleFr());
    mainPage.setTextHy(translation.getTextHy());
    mainPage.setTextEn(translation.getTextEn());
    mainPage.setTextFr(translation.getTextFr());

    mainPage.setBackground(mainPageRequestDto.getBackground());

    return MainPageMapper.toResponseDto(mainPageRepository.save(mainPage));
  }

  public MainPageResponseDto getMainPage() {
    List<MainPage> mainPages = mainPageRepository.findAll();
    if (mainPages.isEmpty())
      return null;
    return MainPageMapper.toResponseDto(mainPages.getFirst());
  }

  public MainPageResponseDto editMainPage(MainPageEditDto mainPageEditDto)
  {
    MainPage mainPage = mainPageRepository.findAll().getFirst();
    mainPage.setTitleHy(mainPageEditDto.getTitle().getHy());
    mainPage.setTitleEn(mainPageEditDto.getTitle().getEn());
    mainPage.setTitleFr(mainPageEditDto.getTitle().getFr());
    mainPage.setTextHy(mainPageEditDto.getText().getHy());
    mainPage.setTextEn(mainPageEditDto.getText().getEn());
    mainPage.setTextFr(mainPageEditDto.getText().getFr());
    mainPage.setBackground(mainPageEditDto.getBackground());

    return MainPageMapper.toResponseDto(mainPageRepository.save(mainPage));
  }

  public QuantityResponseDto getQuantity() {
    Long imagesCount = monumentImageRepository.countMonumentImages();
    Long videosCount = monumentVideoRepository.countMonumentVideos();
    MonumentStatus registeredMonumentStatus = monumentStatusRepository.findById(1).orElseThrow(
        () -> new RuntimeException("Something went wrong"));
    MonumentStatus documentedMonumentStatus = monumentStatusRepository.findById(2).orElseThrow(
        () -> new RuntimeException("Something went wrong"));
    return new QuantityResponseDto(
        new MonumentCountDto(
            monumentRepository.countMonuments(registeredMonumentStatus),
            monumentRepository.getMonumentTypeCounts(registeredMonumentStatus)
        ),
        new MonumentCountDto(
            monumentRepository.countMonuments(documentedMonumentStatus),
            monumentRepository.getMonumentTypeCounts(documentedMonumentStatus)
        ),
        new SettlementsCountDto(
            settlementRepository.countSettlements(),
            settlementRepository.getSettlementCounts()
        ),
        new MediaCountDto(
            imagesCount + videosCount,
            imagesCount,
            videosCount
        )
    );
  }
}
