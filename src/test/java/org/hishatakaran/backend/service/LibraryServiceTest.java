package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private LibraryTranslationService libraryTranslationService;
    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private LibraryService libraryService;

    private static Library library(Long id, String titleHy) {
        Library library = new Library();
        library.setId(id);
        library.setTitleHy(titleHy);
        library.setTitleEn(titleHy + "-en");
        library.setTitleFr(titleHy + "-fr");
        library.setAuthorsHy("Հեղինակ");
        library.setBookUrl("/files/" + id + ".pdf");
        library.setCoverUrl("/images/" + id + ".jpg");
        return library;
    }

    @Test
    void getAll_mapsAndSortsByIdDescending() {
        when(libraryRepository.findAll())
            .thenReturn(List.of(library(1L, "Գիրք Ա"), library(3L, "Գիրք Գ"), library(2L, "Գիրք Բ")));

        List<LibraryResponseDto> result = libraryService.getAll();

        assertThat(result).extracting(LibraryResponseDto::getId).containsExactly(3L, 2L, 1L);
        assertThat(result.get(0).getTitle().getHy()).isEqualTo("Գիրք Գ");
        assertThat(result.get(0).getPdf()).isEqualTo("/files/3.pdf");
        assertThat(result.get(0).getCover()).isEqualTo("/images/3.jpg");
    }

    @Test
    void getAll_returnsEmptyListWhenNoBooks() {
        when(libraryRepository.findAll()).thenReturn(List.of());

        assertThat(libraryService.getAll()).isEmpty();
    }

    @Test
    void getById_returnsMappedBook() {
        when(libraryRepository.findById(4L)).thenReturn(Optional.of(library(4L, "Պատմություն")));

        LibraryResponseDto result = libraryService.getById(4L);

        assertThat(result.getId()).isEqualTo(4L);
        assertThat(result.getTitle().getHy()).isEqualTo("Պատմություն");
        assertThat(result.getAuthors().getHy()).isEqualTo("Հեղինակ");
    }

    @Test
    void getById_throwsWhenBookMissing() {
        when(libraryRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> libraryService.getById(404L))
            .isInstanceOf(NoSuchElementException.class);
    }
}
