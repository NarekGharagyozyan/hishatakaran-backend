package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.PageContent;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.mapper.PageContentMapper;
import org.hishatakaran.backend.model.MainPageRequestDto;
import org.hishatakaran.backend.model.MainPageTranslationDto;
import org.hishatakaran.backend.model.PageContentEditDto;
import org.hishatakaran.backend.model.PageContentRequestDto;
import org.hishatakaran.backend.model.PageContentResponseDto;
import org.hishatakaran.backend.model.PageType;
import org.hishatakaran.backend.repository.PageContentRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

/** The {@link PageType} pages work as the main page does: create translates, edit saves as sent. */
@Service
@RequiredArgsConstructor
public class PageContentService {

  private final PageContentRepository pageContentRepository;
  private final GeminiService geminiService;

  /** Armenian in; Gemini corrects it and adds English and French. Creating again replaces the content. */
  public PageContentResponseDto createPageContent(PageType page, PageContentRequestDto dto) {

    MainPageTranslationDto translation;

    try {
      // the main page's translation fits as is: a title and a text, corrected and translated
      translation = geminiService.translateMainPage(
          new MainPageRequestDto(dto.getTitle(), dto.getDescription(), dto.getBackground())
      );
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    PageContent pageContent = pageContentRepository.findByPage(page)
        .orElseGet(() -> new PageContent(page));

    pageContent.setTitleHy(translation.getTitleHy());
    pageContent.setTitleEn(translation.getTitleEn());
    pageContent.setTitleFr(translation.getTitleFr());
    pageContent.setDescriptionHy(translation.getTextHy());
    pageContent.setDescriptionEn(translation.getTextEn());
    pageContent.setDescriptionFr(translation.getTextFr());
    pageContent.setBackground(dto.getBackground());

    return PageContentMapper.toResponseDto(pageContentRepository.save(pageContent));
  }

  /** Null until the page has been created, as for the main page. */
  public PageContentResponseDto getPageContent(PageType page) {
    return pageContentRepository.findByPage(page)
        .map(PageContentMapper::toResponseDto)
        .orElse(null);
  }

  public PageContentResponseDto editPageContent(PageType page, PageContentEditDto dto) {

    if (dto.getTitle() == null || dto.getDescription() == null) {
      throw new SomethingWentWrongException("Title and description are required");
    }

    PageContent pageContent = pageContentRepository.findByPage(page)
        .orElseThrow(() -> new SomethingWentWrongException("The page has no content yet, create it first"));

    pageContent.setTitleHy(dto.getTitle().getHy());
    pageContent.setTitleEn(dto.getTitle().getEn());
    pageContent.setTitleFr(dto.getTitle().getFr());
    pageContent.setDescriptionHy(dto.getDescription().getHy());
    pageContent.setDescriptionEn(dto.getDescription().getEn());
    pageContent.setDescriptionFr(dto.getDescription().getFr());
    pageContent.setBackground(dto.getBackground());

    return PageContentMapper.toResponseDto(pageContentRepository.save(pageContent));
  }
}
