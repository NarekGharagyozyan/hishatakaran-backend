package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.model.NewsResponseDto;
import org.hishatakaran.backend.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private GeminiService geminiService;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NewsService newsService;

    private static News news(Long id, String titleHy) {
        News news = new News();
        news.setId(id);
        news.setTitleHy(titleHy);
        news.setTitleEn(titleHy + "-en");
        news.setTitleFr(titleHy + "-fr");
        news.setTextHy("տեքստ");
        news.setImages(List.of("/images/news/" + id + ".jpg"));
        news.setIsPublished(Boolean.FALSE);
        return news;
    }

    @Test
    void getAll_mapsEveryNewsKeepingRepositoryOrder() {
        when(newsRepository.findAll())
            .thenReturn(List.of(news(2L, "Նորություն Բ"), news(1L, "Նորություն Ա")));

        List<NewsResponseDto> result = newsService.getAll();

        assertThat(result).extracting(NewsResponseDto::getId).containsExactly(2L, 1L);
        assertThat(result.get(0).getTitle().getHy()).isEqualTo("Նորություն Բ");
        assertThat(result.get(0).getImages()).containsExactly("/images/news/2.jpg");
        assertThat(result.get(0).getIsPublished()).isFalse();
    }

    @Test
    void getAll_returnsEmptyListWhenNoNews() {
        when(newsRepository.findAll()).thenReturn(List.of());

        assertThat(newsService.getAll()).isEmpty();
    }

    @Test
    void getById_returnsMappedNews() {
        when(newsRepository.findById(6L)).thenReturn(Optional.of(news(6L, "Վերջին")));

        NewsResponseDto result = newsService.getById(6L);

        assertThat(result.getId()).isEqualTo(6L);
        assertThat(result.getTitle().getHy()).isEqualTo("Վերջին");
        assertThat(result.getText().getHy()).isEqualTo("տեքստ");
    }

    @Test
    void getById_throwsWhenNewsMissing() {
        when(newsRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsService.getById(404L))
            .isInstanceOf(NoSuchElementException.class);
    }
}
