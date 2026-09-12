package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    private static Region region(Long id, String nameHy) {
        Region region = new Region();
        region.setId(id);
        region.setNameHy(nameHy);
        region.setNameEn(nameHy + "-en");
        region.setNameFr(nameHy + "-fr");
        return region;
    }

    @Test
    void getAll_mapsAndSortsByIdDescending() {
        when(regionRepository.findAll())
            .thenReturn(List.of(region(1L, "Արագածոտն"), region(3L, "Շիրակ"), region(2L, "Լոռի")));

        List<RegionResponseDto> result = regionService.getAll();

        assertThat(result).extracting(RegionResponseDto::getId).containsExactly(3L, 2L, 1L);
        assertThat(result.get(0).getName().getHy()).isEqualTo("Շիրակ");
        assertThat(result.get(0).getName().getEn()).isEqualTo("Շիրակ-en");
    }

    @Test
    void getAll_returnsEmptyListWhenNoRegions() {
        when(regionRepository.findAll()).thenReturn(List.of());

        assertThat(regionService.getAll()).isEmpty();
    }

    @Test
    void getById_returnsMappedRegion() {
        when(regionRepository.findById(5L)).thenReturn(Optional.of(region(5L, "Սյունիք")));

        RegionResponseDto result = regionService.getById(5L);

        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getName().getHy()).isEqualTo("Սյունիք");
    }

    @Test
    void getById_throwsWhenRegionMissing() {
        when(regionRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> regionService.getById(404L))
            .isInstanceOf(NoSuchElementException.class);
    }
}
