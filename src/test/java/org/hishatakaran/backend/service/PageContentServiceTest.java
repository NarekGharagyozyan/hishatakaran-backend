package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hishatakaran.backend.entity.PageContent;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MainPageRequestDto;
import org.hishatakaran.backend.model.MainPageTranslationDto;
import org.hishatakaran.backend.model.PageContentEditDto;
import org.hishatakaran.backend.model.PageContentRequestDto;
import org.hishatakaran.backend.model.PageContentResponseDto;
import org.hishatakaran.backend.model.PageType;
import org.hishatakaran.backend.repository.PageContentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageContentServiceTest {

    @Mock
    private PageContentRepository pageContentRepository;
    @Mock
    private GeminiService geminiService;

    @InjectMocks
    private PageContentService pageContentService;

    private static MainPageTranslationDto translation() {
        MainPageTranslationDto dto = new MainPageTranslationDto();
        dto.setTitleHy("Բնակավայրեր");
        dto.setTitleEn("Settlements");
        dto.setTitleFr("Localités");
        dto.setTextHy("Նկարագրություն");
        dto.setTextEn("Description");
        dto.setTextFr("Description FR");
        return dto;
    }

    private void saving() {
        when(pageContentRepository.save(any(PageContent.class))).thenAnswer(call -> call.getArgument(0));
    }

    @Test
    void createPageContent_translatesTheArmenianAndStoresAllThreeLanguages() throws Exception {
        when(pageContentRepository.findByPage(PageType.settlementItems)).thenReturn(Optional.empty());
        when(geminiService.translateMainPage(any(MainPageRequestDto.class))).thenReturn(translation());
        saving();

        PageContentResponseDto result = pageContentService.createPageContent(
            PageType.settlementItems, new PageContentRequestDto("Բնակավայրեր", "Նկարագրություն", "/images/bg.jpg"));

        ArgumentCaptor<MainPageRequestDto> sent = ArgumentCaptor.forClass(MainPageRequestDto.class);
        verify(geminiService).translateMainPage(sent.capture());
        assertThat(sent.getValue().getTitle()).isEqualTo("Բնակավայրեր");
        assertThat(sent.getValue().getText()).isEqualTo("Նկարագրություն");

        assertThat(result.getTitle()).isEqualTo(new LanguagesResponseDto("Բնակավայրեր", "Settlements", "Localités"));
        assertThat(result.getDescription()).isEqualTo(new LanguagesResponseDto("Նկարագրություն", "Description", "Description FR"));
        assertThat(result.getBackground()).isEqualTo("/images/bg.jpg");

        ArgumentCaptor<PageContent> saved = ArgumentCaptor.forClass(PageContent.class);
        verify(pageContentRepository).save(saved.capture());
        assertThat(saved.getValue().getPage()).isEqualTo(PageType.settlementItems);
    }

    @Test
    void createPageContent_againReplacesTheContentOfThatPage() throws Exception {
        PageContent existing = new PageContent(PageType.monumentPassport);
        existing.setId(4L);
        existing.setTitleEn("old");
        existing.setBackground("/images/old.jpg");
        when(pageContentRepository.findByPage(PageType.monumentPassport)).thenReturn(Optional.of(existing));
        when(geminiService.translateMainPage(any(MainPageRequestDto.class))).thenReturn(translation());
        saving();

        pageContentService.createPageContent(
            PageType.monumentPassport, new PageContentRequestDto("Բնակավայրեր", "Նկարագրություն", "/images/new.jpg"));

        verify(pageContentRepository).save(existing);
        assertThat(existing.getId()).isEqualTo(4L);
        assertThat(existing.getTitleEn()).isEqualTo("Settlements");
        assertThat(existing.getBackground()).isEqualTo("/images/new.jpg");
    }

    @Test
    void getPageContent_answersThatPagesContent() {
        PageContent stored = new PageContent(PageType.documentedMonument);
        stored.setTitleHy("Փաստագրված");
        stored.setBackground("/images/d.jpg");
        when(pageContentRepository.findByPage(PageType.documentedMonument)).thenReturn(Optional.of(stored));

        PageContentResponseDto result = pageContentService.getPageContent(PageType.documentedMonument);

        assertThat(result.getTitle().getHy()).isEqualTo("Փաստագրված");
        assertThat(result.getDescription()).isNull();
        assertThat(result.getBackground()).isEqualTo("/images/d.jpg");
    }

    @Test
    void getPageContent_answersNullBeforeThePageIsCreated() {
        when(pageContentRepository.findByPage(PageType.settlementItems)).thenReturn(Optional.empty());

        assertThat(pageContentService.getPageContent(PageType.settlementItems)).isNull();
    }

    @Test
    void editPageContent_savesAllThreeLanguagesAsSentWithoutTranslating() {
        PageContent stored = new PageContent(PageType.settlementItems);
        when(pageContentRepository.findByPage(PageType.settlementItems)).thenReturn(Optional.of(stored));
        saving();

        PageContentResponseDto result = pageContentService.editPageContent(PageType.settlementItems, new PageContentEditDto(
            new LanguagesResponseDto("Վերնագիր", "Title", "Titre"),
            new LanguagesResponseDto("Տեքստ", "Text", "Texte"),
            "/images/edited.jpg"));

        assertThat(result.getTitle()).isEqualTo(new LanguagesResponseDto("Վերնագիր", "Title", "Titre"));
        assertThat(result.getDescription()).isEqualTo(new LanguagesResponseDto("Տեքստ", "Text", "Texte"));
        assertThat(stored.getBackground()).isEqualTo("/images/edited.jpg");
        verifyNoInteractions(geminiService);
    }

    @Test
    void editPageContent_failsBeforeThePageIsCreated() {
        when(pageContentRepository.findByPage(PageType.monumentPassport)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pageContentService.editPageContent(PageType.monumentPassport, new PageContentEditDto(
            new LanguagesResponseDto("ա", "a", "a"), new LanguagesResponseDto("բ", "b", "b"), null)))
            .isInstanceOf(SomethingWentWrongException.class);
        verify(pageContentRepository, never()).save(any());
    }

    @Test
    void editPageContent_needsTheTitleAndTheDescription() {
        assertThatThrownBy(() -> pageContentService.editPageContent(PageType.settlementItems,
            new PageContentEditDto(null, new LanguagesResponseDto("բ", "b", "b"), null)))
            .isInstanceOf(SomethingWentWrongException.class);
        verifyNoInteractions(pageContentRepository);
    }
}
