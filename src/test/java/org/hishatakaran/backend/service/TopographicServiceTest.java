package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.TopographicResponseDto;
import org.hishatakaran.backend.repository.TopographicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopographicServiceTest {

    @Mock
    private TopographicRepository topographicRepository;

    @InjectMocks
    private TopographicService topographicService;

    private static Topographic topographic(Long id, String addressHy) {
        Topographic topographic = new Topographic();
        topographic.setId(id);
        topographic.setAddressHy(addressHy);
        topographic.setAddressEn(addressHy + "-en");
        topographic.setAddressFr(addressHy + "-fr");
        topographic.setLatitude("40.1");
        topographic.setLongitude("44.5");
        return topographic;
    }

    @Test
    void getAll_mapsEveryTopographicKeepingRepositoryOrder() {
        when(topographicRepository.findAll())
            .thenReturn(List.of(topographic(2L, "Երևան"), topographic(1L, "Գյումրի")));

        List<TopographicResponseDto> result = topographicService.getAll();

        assertThat(result).extracting(TopographicResponseDto::getId).containsExactly(2L, 1L);
        assertThat(result.get(0).getAddress().getHy()).isEqualTo("Երևան");
    }

    @Test
    void getAll_returnsEmptyListWhenNothingStored() {
        when(topographicRepository.findAll()).thenReturn(List.of());

        assertThat(topographicService.getAll()).isEmpty();
    }

    @Test
    void getById_returnsMappedTopographic() {
        when(topographicRepository.findById(7L)).thenReturn(Optional.of(topographic(7L, "Աշտարակ")));

        TopographicResponseDto result = topographicService.getById(7L);

        assertThat(result.getId()).isEqualTo(7L);
        assertThat(result.getAddress().getHy()).isEqualTo("Աշտարակ");
        assertThat(result.getLatitude()).isEqualTo("40.1");
    }

    @Test
    void getById_throwsWhenTopographicMissing() {
        when(topographicRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> topographicService.getById(404L))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getByMonument_mapsOnlyTheMonumentsTopographics() {
        when(topographicRepository.findByMonumentId(42L))
            .thenReturn(List.of(topographic(9L, "Օշական")));

        List<TopographicResponseDto> result = topographicService.getByMonument(42L);

        assertThat(result).extracting(TopographicResponseDto::getId).containsExactly(9L);
    }

    @Test
    void getByMonument_returnsEmptyListWhenMonumentHasNone() {
        when(topographicRepository.findByMonumentId(42L)).thenReturn(List.of());

        assertThat(topographicService.getByMonument(42L)).isEmpty();
    }
}
