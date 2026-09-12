package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @Mock
    private GeminiService geminiService;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private SettlementRepository settlementRepository;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private MonumentService monumentService;

    @InjectMocks
    private SettlementService settlementService;

    private static Settlement settlement(Long id, String nameHy, Long regionId) {
        Region region = new Region();
        region.setId(regionId);
        region.setNameHy("մարզ");

        Settlement settlement = new Settlement();
        settlement.setId(id);
        settlement.setNameHy(nameHy);
        settlement.setNameEn(nameHy + "-en");
        settlement.setNameFr(nameHy + "-fr");
        settlement.setDescriptionHy("նկարագրություն");
        settlement.setLatitude("40.1");
        settlement.setLongitude("44.5");
        settlement.setRegion(region);
        return settlement;
    }

    @Test
    void getByRegion_mapsOnlyTheRegionsSettlementsKeepingRepositoryOrder() {
        when(settlementRepository.findAllByRegionId(7L))
            .thenReturn(List.of(settlement(1L, "Օշական", 7L), settlement(2L, "Աշտարակ", 7L)));

        List<SettlementResponseDto> result = settlementService.getByRegion(7L);

        assertThat(result).extracting(SettlementResponseDto::getId).containsExactly(1L, 2L);
        assertThat(result).extracting(SettlementResponseDto::getRegionId).containsOnly(7L);
        assertThat(result.get(0).getName().getHy()).isEqualTo("Օշական");
    }

    @Test
    void getByRegion_returnsEmptyListWhenRegionHasNoSettlements() {
        when(settlementRepository.findAllByRegionId(7L)).thenReturn(List.of());

        assertThat(settlementService.getByRegion(7L)).isEmpty();
    }

    @Test
    void getAll_mapsAndSortsByIdDescending() {
        when(settlementRepository.findAll())
            .thenReturn(List.of(settlement(1L, "Ա", 7L), settlement(3L, "Գ", 8L), settlement(2L, "Բ", 7L)));

        List<SettlementResponseDto> result = settlementService.getAll();

        assertThat(result).extracting(SettlementResponseDto::getId).containsExactly(3L, 2L, 1L);
        assertThat(result.get(0).getRegionId()).isEqualTo(8L);
        assertThat(result.get(0).getLatitude()).isEqualTo("40.1");
    }

    @Test
    void getAll_returnsEmptyListWhenNoSettlements() {
        when(settlementRepository.findAll()).thenReturn(List.of());

        assertThat(settlementService.getAll()).isEmpty();
    }
}
