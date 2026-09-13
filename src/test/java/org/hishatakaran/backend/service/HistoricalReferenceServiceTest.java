package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;
import org.hishatakaran.backend.repository.HistoricalReferenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HistoricalReferenceServiceTest {

    @Mock
    private HistoricalReferenceRepository historicalReferenceRepository;

    @InjectMocks
    private HistoricalReferenceService historicalReferenceService;

    private static HistoricalReference historicalReference(Long id, String authorHy) {
        HistoricalReference reference = new HistoricalReference();
        reference.setId(id);
        reference.setAuthorHy(authorHy);
        reference.setAuthorEn(authorHy + "-en");
        reference.setAuthorFr(authorHy + "-fr");
        reference.setCulturalAffiliationHy("հայկական");
        return reference;
    }

    @Test
    void getAll_mapsEveryReferenceKeepingRepositoryOrder() {
        when(historicalReferenceRepository.findAll())
            .thenReturn(List.of(historicalReference(2L, "Խորենացի"), historicalReference(1L, "Բուզանդ")));

        List<HistoricalReferenceResponseDto> result = historicalReferenceService.getAll();

        assertThat(result).extracting(HistoricalReferenceResponseDto::getId).containsExactly(2L, 1L);
        assertThat(result.get(0).getAuthor().getHy()).isEqualTo("Խորենացի");
        assertThat(result.get(0).getCulturalAffiliation().getHy()).isEqualTo("հայկական");
    }

    @Test
    void getAll_returnsEmptyListWhenNothingStored() {
        when(historicalReferenceRepository.findAll()).thenReturn(List.of());

        assertThat(historicalReferenceService.getAll()).isEmpty();
    }

    @Test
    void getById_returnsMappedReference() {
        when(historicalReferenceRepository.findById(3L))
            .thenReturn(Optional.of(historicalReference(3L, "Օրբելյան")));

        HistoricalReferenceResponseDto result = historicalReferenceService.getById(3L);

        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getAuthor().getEn()).isEqualTo("Օրբելյան-en");
    }

    @Test
    void getById_throwsWhenReferenceMissing() {
        when(historicalReferenceRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> historicalReferenceService.getById(404L))
            .isInstanceOf(NoSuchElementException.class);
    }
}
