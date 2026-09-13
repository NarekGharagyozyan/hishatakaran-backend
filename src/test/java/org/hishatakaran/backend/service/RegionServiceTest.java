package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.RegionEditDto;
import org.hishatakaran.backend.model.RegionRequestDto;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.model.RegionTranslationDto;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;
    @Mock
    private SettlementRepository settlementRepository;
    @Mock
    private MonumentRepository monumentRepository;
    @Mock
    private GeminiService geminiService;

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

    private static Settlement settlement(String nameHy) {
        Settlement settlement = new Settlement();
        settlement.setNameHy(nameHy);
        return settlement;
    }

    private static Monument monument(String nameHy) {
        Monument monument = new Monument();
        monument.setNameHy(nameHy);
        return monument;
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

    @Test
    void createNewRegion_savesTheTranslatedNameInAllThreeLanguages() throws Exception {
        when(geminiService.translateRegion("Արագածոտն"))
            .thenReturn(new RegionTranslationDto("Արագածոտն", "Aragatsotn", "Aragatsotn"));
        when(regionRepository.save(any(Region.class))).thenAnswer(call -> call.getArgument(0));

        RegionResponseDto result = regionService.createNewRegion(new RegionRequestDto("Արագածոտն"));

        ArgumentCaptor<Region> saved = ArgumentCaptor.forClass(Region.class);
        verify(regionRepository).save(saved.capture());
        assertThat(saved.getValue().getNameHy()).isEqualTo("Արագածոտն");
        assertThat(saved.getValue().getNameEn()).isEqualTo("Aragatsotn");
        assertThat(saved.getValue().getNameFr()).isEqualTo("Aragatsotn");
        assertThat(result.getName().getEn()).isEqualTo("Aragatsotn");
    }

    @Test
    void createNewRegion_throwsAndSavesNothingWhenTranslationFails() throws Exception {
        when(geminiService.translateRegion("Լոռի"))
            .thenThrow(new JsonProcessingException("broken json") {
            });

        assertThatThrownBy(() -> regionService.createNewRegion(new RegionRequestDto("Լոռի")))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Failed to translate region");

        verify(regionRepository, never()).save(any());
    }

    @Test
    void editRegion_overwritesAllThreeNames() {
        when(regionRepository.findById(2L)).thenReturn(Optional.of(region(2L, "հին")));
        when(regionRepository.save(any(Region.class))).thenAnswer(call -> call.getArgument(0));

        RegionResponseDto result = regionService.editRegion(
            2L,
            new RegionEditDto(new LanguagesResponseDto("Շիրակ", "Shirak", "Chirak"))
        );

        assertThat(result.getName().getHy()).isEqualTo("Շիրակ");
        assertThat(result.getName().getEn()).isEqualTo("Shirak");
        assertThat(result.getName().getFr()).isEqualTo("Chirak");
    }

    @Test
    void editRegion_throwsWhenRegionMissing() {
        when(regionRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> regionService.editRegion(
            404L,
            new RegionEditDto(new LanguagesResponseDto("Շիրակ", "Shirak", "Chirak"))
        ))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Region not found");
    }

    @Test
    void deleteRegion_deletesWhenNothingReferencesTheRegion() {
        Region region = region(3L, "Վայոց ձոր");
        when(regionRepository.findById(3L)).thenReturn(Optional.of(region));
        when(settlementRepository.findAllByRegionId(3L)).thenReturn(List.of());
        when(monumentRepository.findByRegionId(3L)).thenReturn(List.of());

        regionService.deleteRegion(3L);

        verify(regionRepository).delete(region);
    }

    @Test
    void deleteRegion_throwsAndKeepsRegionWhenSettlementsStillUseIt() {
        when(regionRepository.findById(3L)).thenReturn(Optional.of(region(3L, "Լոռի")));
        when(settlementRepository.findAllByRegionId(3L))
            .thenReturn(List.of(settlement("Ալավերդի"), settlement("Ստեփանավան")));

        assertThatThrownBy(() -> regionService.deleteRegion(3L))
            .isInstanceOf(SomethingWentWrongException.class)
            .hasMessageContaining("Ալավերդի, Ստեփանավան");

        verify(regionRepository, never()).delete(any());
    }

    @Test
    void deleteRegion_throwsAndKeepsRegionWhenMonumentsStillUseIt() {
        when(regionRepository.findById(3L)).thenReturn(Optional.of(region(3L, "Կոտայք")));
        when(settlementRepository.findAllByRegionId(3L)).thenReturn(List.of());
        when(monumentRepository.findByRegionId(3L))
            .thenReturn(List.of(monument("Գառնի"), monument("Գեղարդ")));

        assertThatThrownBy(() -> regionService.deleteRegion(3L))
            .isInstanceOf(SomethingWentWrongException.class)
            .hasMessageContaining("Գառնի, Գեղարդ");

        verify(regionRepository, never()).delete(any());
    }

    @Test
    void deleteRegion_throwsWhenRegionMissing() {
        when(regionRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> regionService.deleteRegion(404L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Region not found");

        verify(regionRepository, never()).delete(any());
    }
}
