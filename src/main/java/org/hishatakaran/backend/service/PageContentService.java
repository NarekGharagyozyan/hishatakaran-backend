package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.PageContent;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.mapper.PageContentMapper;
import org.hishatakaran.backend.model.LanguagesResponseDto;
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

  public PageContentResponseDto createPageContent(PageType page, PageContentRequestDto dto) {

    boolean hasTitle = dto.getTitle() != null;
    boolean hasDescription = dto.getDescription() != null;

    MainPageTranslationDto translation = hasTitle || hasDescription ? translate(dto) : new MainPageTranslationDto();

    PageContent pageContent = pageContentRepository.findByPage(page)
        .orElseGet(() -> new PageContent(page));

    pageContent.setTitleHy(hasTitle ? translation.getTitleHy() : null);
    pageContent.setTitleEn(hasTitle ? translation.getTitleEn() : null);
    pageContent.setTitleFr(hasTitle ? translation.getTitleFr() : null);
    pageContent.setDescriptionHy(hasDescription ? translation.getTextHy() : null);
    pageContent.setDescriptionEn(hasDescription ? translation.getTextEn() : null);
    pageContent.setDescriptionFr(hasDescription ? translation.getTextFr() : null);
    pageContent.setBackground(dto.getBackground());

    return PageContentMapper.toResponseDto(pageContentRepository.save(pageContent));
  }

  private MainPageTranslationDto translate(PageContentRequestDto dto) {
    try {
      return geminiService.translateMainPage(
          new MainPageRequestDto(dto.getTitle(), dto.getDescription(), dto.getBackground())
      );
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public PageContentResponseDto getPageContent(PageType page) {
    return pageContentRepository.findByPage(page)
        .map(PageContentMapper::toResponseDto)
        .orElse(null);
  }

  public PageContentResponseDto editPageContent(PageType page, PageContentEditDto dto) {

    PageContent pageContent = pageContentRepository.findByPage(page)
        .orElseThrow(() -> new SomethingWentWrongException("The page has no content yet, create it first"));

    LanguagesResponseDto title = dto.getTitle();
    LanguagesResponseDto description = dto.getDescription();

    pageContent.setTitleHy(title == null ? null : title.getHy());
    pageContent.setTitleEn(title == null ? null : title.getEn());
    pageContent.setTitleFr(title == null ? null : title.getFr());
    pageContent.setDescriptionHy(description == null ? null : description.getHy());
    pageContent.setDescriptionEn(description == null ? null : description.getEn());
    pageContent.setDescriptionFr(description == null ? null : description.getFr());
    pageContent.setBackground(dto.getBackground());

    return PageContentMapper.toResponseDto(pageContentRepository.save(pageContent));
  }
}
