package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentStatus;
import org.hishatakaran.backend.entity.MonumentTypes;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentStatusResponseDto;
import org.hishatakaran.backend.model.MonumentTypesResponseDto;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.MonumentStatusRepository;
import org.hishatakaran.backend.repository.MonumentTypesRepository;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MonumentServiceTest {

    @Mock
    private MonumentRepository monumentRepository;
    @Mock
    private GeminiService geminiService;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private SettlementRepository settlementRepository;
    @Mock
    private MonumentTypesRepository monumentTypesRepository;
    @Mock
    private MonumentStatusRepository monumentStatusRepository;
    @Mock
    private MonumentTranslationService monumentTranslationService;
    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private MonumentService monumentService;

    private static Monument monument(Long id, String nameHy) {
        Monument monument = new Monument();
        monument.setId(id);
        monument.setNameHy(nameHy);
        monument.setNameEn(nameHy + "-en");
        monument.setNameFr(nameHy + "-fr");
        monument.setIsPublished(Boolean.TRUE);
        // MonumentMapper streams these and reads the timestamps, so they must be set.
        monument.setBibliography(List.of());
        monument.setCreatedAt(ZonedDateTime.now());
        monument.setUpdatedAt(ZonedDateTime.now());
        // These mappers dereference their argument without a null check.
        monument.setMonumentType(monumentType(1L, "եկեղեցի"));
        monument.setTopographics(new Topographic());
        monument.setHistoricalReferences(new HistoricalReference());
        monument.setDescriptiveCharacteristics(new DescriptiveCharacteristicReference());
        return monument;
    }

    private static MonumentTypes monumentType(Long id, String nameHy) {
        MonumentTypes monumentType = new MonumentTypes();
        monumentType.setId(id);
        monumentType.setNameHy(nameHy);
        monumentType.setNameEn(nameHy + "-en");
        monumentType.setNameFr(nameHy + "-fr");
        return monumentType;
    }

    private static MonumentStatus monumentStatus(Long id, String nameHy) {
        MonumentStatus monumentStatus = new MonumentStatus();
        monumentStatus.setId(id);
        monumentStatus.setNameHy(nameHy);
        monumentStatus.setNameEn(nameHy + "-en");
        monumentStatus.setNameFr(nameHy + "-fr");
        return monumentStatus;
    }

    @Test
    void getById_returnsMappedMonument() {
        when(monumentRepository.findById(11L)).thenReturn(Optional.of(monument(11L, "Գառնի")));

        MonumentResponseDto result = monumentService.getById(11L);

        assertThat(result.getId()).isEqualTo(11L);
        assertThat(result.getName().getHy()).isEqualTo("Գառնի");
        assertThat(result.getIsPublished()).isTrue();
    }

    @Test
    void getById_throwsWhenMonumentMissing() {
        when(monumentRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> monumentService.getById(404L))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getByRegion_mapsOnlyTheRegionsMonuments() {
        when(monumentRepository.findByRegionId(3L))
            .thenReturn(List.of(monument(1L, "Զվարթնոց"), monument(2L, "Էջմիածին")));

        List<MonumentResponseDto> result = monumentService.getByRegion(3L);

        assertThat(result).extracting(MonumentResponseDto::getId).containsExactly(1L, 2L);
        assertThat(result.get(0).getName().getHy()).isEqualTo("Զվարթնոց");
    }

    @Test
    void getByRegion_returnsEmptyListWhenRegionHasNoMonuments() {
        when(monumentRepository.findByRegionId(3L)).thenReturn(List.of());

        assertThat(monumentService.getByRegion(3L)).isEmpty();
    }

    @Test
    void getBySettlement_mapsOnlyTheSettlementsMonuments() {
        when(monumentRepository.findBySettlementId(5L))
            .thenReturn(List.of(monument(8L, "Օշականի եկեղեցի")));

        List<MonumentResponseDto> result = monumentService.getBySettlement(5L);

        assertThat(result).extracting(MonumentResponseDto::getId).containsExactly(8L);
    }

    @Test
    void getBySettlement_returnsEmptyListWhenSettlementHasNoMonuments() {
        when(monumentRepository.findBySettlementId(5L)).thenReturn(List.of());

        assertThat(monumentService.getBySettlement(5L)).isEmpty();
    }

    @Test
    void getAllMonumentTypes_mapsEveryType() {
        when(monumentTypesRepository.findAll())
            .thenReturn(List.of(monumentType(1L, "եկեղեցի"), monumentType(2L, "խաչքար")));

        List<MonumentTypesResponseDto> result = monumentService.getAllMonumentTypes();

        assertThat(result).extracting(MonumentTypesResponseDto::getId).containsExactly(1L, 2L);
        assertThat(result.get(0).getTypes().getHy()).isEqualTo("եկեղեցի");
        assertThat(result.get(1).getTypes().getFr()).isEqualTo("խաչքար-fr");
    }

    @Test
    void getAllMonumentTypes_returnsEmptyListWhenNoTypes() {
        when(monumentTypesRepository.findAll()).thenReturn(List.of());

        assertThat(monumentService.getAllMonumentTypes()).isEmpty();
    }

    @Test
    void getAllMonumentStatuses_mapsEveryStatus() {
        when(monumentStatusRepository.findAll())
            .thenReturn(List.of(monumentStatus(1L, "պահպանված"), monumentStatus(2L, "ավերված")));

        List<MonumentStatusResponseDto> result = monumentService.getAllMonumentStatuses();

        assertThat(result).extracting(MonumentStatusResponseDto::getId).containsExactly(1L, 2L);
        assertThat(result.get(0).getName().getHy()).isEqualTo("պահպանված");
    }

    @Test
    void getAllMonumentStatuses_returnsEmptyListWhenNoStatuses() {
        when(monumentStatusRepository.findAll()).thenReturn(List.of());

        assertThat(monumentService.getAllMonumentStatuses()).isEmpty();
    }

    @Test
    void deleteMonumentType_deletesWhenNoMonumentUsesIt() {
        when(monumentRepository.findByMonumentTypeId(4L)).thenReturn(List.of());

        monumentService.deleteMonumentType(4L);

        verify(monumentTypesRepository).deleteById(4L);
    }

    @Test
    void deleteMonumentType_throwsAndKeepsTypeWhenMonumentsStillUseIt() {
        when(monumentRepository.findByMonumentTypeId(4L))
            .thenReturn(List.of(monument(1L, "Գառնի"), monument(2L, "Զվարթնոց")));

        assertThatThrownBy(() -> monumentService.deleteMonumentType(4L))
            .isInstanceOf(SomethingWentWrongException.class)
            .hasMessageContaining("Գառնի, Զվարթնոց");

        verify(monumentTypesRepository, never()).deleteById(4L);
    }
}
