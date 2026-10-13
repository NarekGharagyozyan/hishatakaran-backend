package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementBibliography;
import org.hishatakaran.backend.entity.SettlementImage;
import org.hishatakaran.backend.entity.SettlementMeasurement;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.entity.SettlementType;
import org.hishatakaran.backend.entity.SettlementVideo;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.model.BibliographyRequestDto;
import org.hishatakaran.backend.model.BibliographyResponseDto;
import org.hishatakaran.backend.model.ImageRequestDto;
import org.hishatakaran.backend.model.ImageResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MeasurementRequestDto;
import org.hishatakaran.backend.model.MeasurementResponseDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentVideoRequestDto;
import org.hishatakaran.backend.model.MonumentVideoResponseDto;
import org.hishatakaran.backend.model.SettlementDetailsResponseDto;
import org.hishatakaran.backend.model.SettlementEditDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementTopographicRequestDto;
import org.hishatakaran.backend.model.SettlementTopographicResponseDto;
import org.hishatakaran.backend.model.SettlementTypeResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.hishatakaran.backend.repository.SettlementTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @Mock
    private RegionRepository regionRepository;
    @Mock
    private SettlementRepository settlementRepository;
    @Mock
    private SettlementTypeRepository settlementTypeRepository;
    @Mock
    private SettlementTranslationService settlementTranslationService;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private MonumentService monumentService;

    @InjectMocks
    private SettlementService settlementService;

    // ---------- fixtures ----------

    private static Region region(Long id) {
        Region region = new Region();
        region.setId(id);
        region.setNameHy("մարզ");
        return region;
    }

    private static Settlement settlement(Long id, String nameHy, Long regionId) {
        Settlement settlement = new Settlement();
        settlement.setId(id);
        settlement.setNameHy(nameHy);
        settlement.setNameEn(nameHy + "-en");
        settlement.setNameFr(nameHy + "-fr");
        settlement.setRegion(region(regionId));
        return settlement;
    }

    private static SettlementTopographic coordinates(Settlement settlement, String latitude, String longitude) {
        SettlementTopographic topographic = new SettlementTopographic();
        topographic.setSettlement(settlement);
        topographic.setLatitude(latitude);
        topographic.setLongitude(longitude);
        settlement.setTopographics(topographic);
        return topographic;
    }

    private static SettlementType type(Long id, String nameHy) {
        SettlementType type = new SettlementType(nameHy, nameHy + "-en", nameHy + "-fr");
        type.setId(id);
        return type;
    }

    private static LanguagesResponseDto group(String base) {
        return new LanguagesResponseDto(base, base + "-en", base + "-fr");
    }

    private Settlement stubEditOf(Long id) {
        Settlement existing = settlement(id, "Չրաղ", 5L);
        when(settlementRepository.findById(id)).thenReturn(Optional.of(existing));
        when(settlementRepository.save(any(Settlement.class))).thenAnswer(call -> call.getArgument(0));
        return existing;
    }

    private void stubCreate() {
        when(regionRepository.findById(5L)).thenReturn(Optional.of(region(5L)));
        when(settlementRepository.save(any(Settlement.class))).thenAnswer(call -> call.getArgument(0));
    }

    // ---------- read ----------

    @Test
    void getByRegion_mapsOnlyTheRegionsSettlementsKeepingRepositoryOrder() {
        when(settlementRepository.findAllByRegionId(7L))
            .thenReturn(List.of(settlement(1L, "Օշական", 7L), settlement(2L, "Աշտարակ", 7L)));

        List<SettlementDetailsResponseDto> result = settlementService.getByRegion(7L);

        assertThat(result).extracting(SettlementDetailsResponseDto::getId).containsExactly(1L, 2L);
        assertThat(result).extracting(SettlementDetailsResponseDto::getRegionId).containsOnly(7L);
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

        List<SettlementDetailsResponseDto> result = settlementService.getAll();

        assertThat(result).extracting(SettlementDetailsResponseDto::getId).containsExactly(3L, 2L, 1L);
        assertThat(result.get(0).getRegionId()).isEqualTo(8L);
    }

    @Test
    void getAll_returnsEmptyListWhenNoSettlements() {
        when(settlementRepository.findAll()).thenReturn(List.of());

        assertThat(settlementService.getAll()).isEmpty();
    }

    /** Each item of the list is exactly what GET /settlements/{id} returns for it. */
    @Test
    void getAll_returnsEverySettlementInTheSameFullFormAsTheSingleSettlement() {
        Settlement withDossier = settlement(1L, "Ա", 7L);
        withDossier.setHistoryHy("պատմություն");
        withDossier.setSettlementType(type(3L, "գյուղ"));
        coordinates(withDossier, "39.9", "45.9");
        Settlement bare = settlement(2L, "Բ", 7L);
        when(settlementRepository.findAll()).thenReturn(List.of(withDossier, bare));
        when(settlementRepository.findById(1L)).thenReturn(Optional.of(withDossier));

        List<SettlementDetailsResponseDto> result = settlementService.getAll();

        assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(settlementService.getSettlement(1L));
        assertThat(result.get(1).getHistory().getHy()).isEqualTo("պատմություն");
        assertThat(result.get(1).getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.get(0).getTopographics()).isNull();
    }

    @Test
    void getSettlement_returnsTheWholeDossierWithCoordinatesInsideTopographics() {
        Settlement withDossier = settlement(3L, "Օշական", 5L);
        withDossier.setHistoryHy("պատմություն");
        withDossier.setSettlementType(type(3L, "գյուղ"));
        coordinates(withDossier, "39.9", "45.9").setAddressHy("հասցե");
        withDossier.getMeasurements().add(new SettlementMeasurement("/m.jpg", "հ", null, null, withDossier));
        when(settlementRepository.findById(3L)).thenReturn(Optional.of(withDossier));

        SettlementDetailsResponseDto result = settlementService.getSettlement(3L);

        assertThat(result.getName().getHy()).isEqualTo("Օշական");
        assertThat(result.getHistory().getHy()).isEqualTo("պատմություն");
        assertThat(result.getSettlementType().getName().getEn()).isEqualTo("գյուղ-en");
        assertThat(result.getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
        assertThat(result.getTopographics().getAddress().getHy()).isEqualTo("հասցե");
        assertThat(result.getMeasurements()).hasSize(1);
    }

    @Test
    void getSettlement_throwsWhenMissing() {
        when(settlementRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> settlementService.getSettlement(404L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Settlement not found");
    }

    // ---------- settlement types ----------

    @Test
    void getAllSettlementTypes_returnsEveryTypeInIdOrderForTheDropdown() {
        when(settlementTypeRepository.findAll(Sort.by("id")))
            .thenReturn(List.of(type(1L, "քաղաք"), type(3L, "գյուղ")));

        List<SettlementTypeResponseDto> result = settlementService.getAllSettlementTypes();

        assertThat(result).extracting(SettlementTypeResponseDto::getId).containsExactly(1L, 3L);
        assertThat(result.get(0).getName().getHy()).isEqualTo("քաղաք");
        assertThat(result.get(0).getName().getEn()).isEqualTo("քաղաք-en");
        assertThat(result.get(1).getName().getFr()).isEqualTo("գյուղ-fr");
    }

    @Test
    void getAllSettlementTypes_returnsEmptyListWhenNoneExist() {
        when(settlementTypeRepository.findAll(Sort.by("id"))).thenReturn(List.of());

        assertThat(settlementService.getAllSettlementTypes()).isEmpty();
    }

    // ---------- create ----------

    @Test
    void createNewSettlement_storesTheArmenianTextOnlyAndTranslatesNothing() {
        stubCreate();
        when(settlementTypeRepository.findById(3L)).thenReturn(Optional.of(type(3L, "գյուղ")));

        SettlementRequestDto request = new SettlementRequestDto();
        request.setName("Չրաղ");
        request.setSettlementTypeId(3L);
        request.setAnotherNames("Ջրաղ");
        request.setHistory("Հիմնադրվել է 5-րդ դարում");
        request.setDescription("Բնակավայր");
        request.setDataUpdate("<p>Թարմացվել է 2026</p><img src=\"/images/monuments/x.jpg\">");

        SettlementDetailsResponseDto result = settlementService.createNewSettlement(5L, request);

        assertThat(result.getName().getHy()).isEqualTo("Չրաղ");
        assertThat(result.getRegionId()).isEqualTo(5L);
        assertThat(result.getSettlementType().getId()).isEqualTo(3L);
        assertThat(result.getSettlementType().getName().getHy()).isEqualTo("գյուղ");
        assertThat(result.getAnotherNames().getHy()).isEqualTo("Ջրաղ");
        assertThat(result.getHistory().getHy()).isEqualTo("Հիմնադրվել է 5-րդ դարում");
        assertThat(result.getDescription().getHy()).isEqualTo("Բնակավայր");
        assertThat(result.getDataUpdate().getHy()).contains("<img src=\"/images/monuments/x.jpg\">");

        // English and French are left for the translate endpoint, exactly as for monuments.
        assertThat(result.getHistory().getEn()).isNull();
        assertThat(result.getHistory().getFr()).isNull();
        verifyNoInteractions(settlementTranslationService);
    }

    @Test
    void createNewSettlement_givesTheNotNullNameColumnsAnEmptyStringUntilTranslated() {
        stubCreate();

        SettlementRequestDto request = new SettlementRequestDto();
        request.setName("Չրաղ");

        SettlementDetailsResponseDto result = settlementService.createNewSettlement(5L, request);

        // settlements.name_en / name_fr are NOT NULL, so null would fail the insert.
        assertThat(result.getName().getEn()).isEmpty();
        assertThat(result.getName().getFr()).isEmpty();
    }

    @Test
    void createNewSettlement_keepsTheCoordinatesInsideTheTopographicBlock() {
        stubCreate();

        SettlementRequestDto request = new SettlementRequestDto();
        request.setName("Չրաղ");
        request.setTopographics(new SettlementTopographicRequestDto(
            "Սարահարթի վրա", "Գլխավոր փողոց 1", "39.9", "45.9", "1200 մ", "Քասաղ գետ", "Ստուգված"
        ));

        SettlementDetailsResponseDto result = settlementService.createNewSettlement(5L, request);

        assertThat(result.getTopographics().getTopography().getHy()).isEqualTo("Սարահարթի վրա");
        assertThat(result.getTopographics().getAddress().getHy()).isEqualTo("Գլխավոր փողոց 1");
        assertThat(result.getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
        assertThat(result.getTopographics().getAltitude().getHy()).isEqualTo("1200 մ");
        assertThat(result.getTopographics().getHydrography().getHy()).isEqualTo("Քասաղ գետ");
        assertThat(result.getTopographics().getDataUpdate().getHy()).isEqualTo("Ստուգված");
    }

    @Test
    void createNewSettlement_storesEveryListWithArmenianOnly() {
        stubCreate();

        SettlementRequestDto request = new SettlementRequestDto();
        request.setName("Չրաղ");
        request.setBibliography(List.of(new BibliographyRequestDto("Արցախի պատմություն", "/files/b.pdf")));
        request.setImages(List.of(new ImageRequestDto(null, "/images/x.jpg", "Ընդհանուր տեսք")));
        request.setMeasurements(List.of(new MeasurementRequestDto(null, "/images/m.jpg", "Հատակագիծ")));
        request.setVideos(List.of(new MonumentVideoRequestDto("Ֆիլմ Չրաղի մասին", "https://youtu.be/abc")));

        SettlementDetailsResponseDto result = settlementService.createNewSettlement(5L, request);

        assertThat(result.getBibliography().get(0).getTitle().getHy()).isEqualTo("Արցախի պատմություն");
        assertThat(result.getBibliography().get(0).getUrl()).isEqualTo("/files/b.pdf");
        assertThat(result.getImages().get(0).getUrl()).isEqualTo("/images/x.jpg");
        assertThat(result.getImages().get(0).getCaption().getHy()).isEqualTo("Ընդհանուր տեսք");
        assertThat(result.getImages().get(0).getCaption().getEn()).isNull();
        assertThat(result.getMeasurements().get(0).getCaption().getHy()).isEqualTo("Հատակագիծ");
        assertThat(result.getMeasurements().get(0).getUrl()).isEqualTo("/images/m.jpg");
        assertThat(result.getVideos().get(0).getTitle().getHy()).isEqualTo("Ֆիլմ Չրաղի մասին");
        assertThat(result.getVideos().get(0).getUrl()).isEqualTo("https://youtu.be/abc");
    }

    @Test
    void createNewSettlement_worksWithoutATypeOrAnyOptionalSection() {
        stubCreate();

        SettlementRequestDto request = new SettlementRequestDto();
        request.setName("Չրաղ");

        SettlementDetailsResponseDto result = settlementService.createNewSettlement(5L, request);

        assertThat(result.getSettlementType()).isNull();
        assertThat(result.getTopographics()).isNull();
        assertThat(result.getBibliography()).isEmpty();
        assertThat(result.getImages()).isEmpty();
        assertThat(result.getMeasurements()).isEmpty();
        assertThat(result.getVideos()).isEmpty();
        verify(settlementTypeRepository, never()).findById(any());
    }

    @Test
    void createNewSettlement_failsWhenTheTypeDoesNotExist() {
        when(regionRepository.findById(5L)).thenReturn(Optional.of(region(5L)));
        when(settlementTypeRepository.findById(99L)).thenReturn(Optional.empty());

        SettlementRequestDto request = new SettlementRequestDto();
        request.setName("Չրաղ");
        request.setSettlementTypeId(99L);

        assertThatThrownBy(() -> settlementService.createNewSettlement(5L, request))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Settlement type not found");
        verify(settlementRepository, never()).save(any());
    }

    @Test
    void createNewSettlement_failsWhenTheRegionDoesNotExist() {
        when(regionRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> settlementService.createNewSettlement(404L, new SettlementRequestDto()))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Region not found");
        verify(settlementRepository, never()).save(any());
    }

    // ---------- edit ----------

    @Test
    void editSettlement_savesAllThreeLanguagesAsSent() {
        stubEditOf(393L);

        SettlementEditDto dto = new SettlementEditDto();
        dto.setName(group("Չրաղ"));
        dto.setAnotherNames(group("Ջրաղ"));
        dto.setHistory(group("պատմություն"));
        dto.setDescription(group("նկարագրություն"));
        dto.setDataUpdate(group("թարմացում"));

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getName().getEn()).isEqualTo("Չրաղ-en");
        assertThat(result.getAnotherNames().getEn()).isEqualTo("Ջրաղ-en");
        assertThat(result.getHistory().getHy()).isEqualTo("պատմություն");
        assertThat(result.getDescription().getFr()).isEqualTo("նկարագրություն-fr");
        assertThat(result.getDataUpdate().getEn()).isEqualTo("թարմացում-en");
    }

    /**
     * The reported case: a caption added in the form with Armenian only. The translator is asked
     * to fill the gaps of the settlement as just edited, and the answer carries the result.
     */
    @Test
    void editSettlement_asksForTheMissingTranslationsOfTextTheAdminJustAdded() {
        Settlement existing = stubEditOf(393L);
        doAnswer(call -> {
            Settlement edited = call.getArgument(0);
            assertThat(edited.getImages()).hasSize(1);                       // it sees the edit already applied
            assertThat(edited.getImages().get(0).getCaptionEn()).isEmpty();  // and the gap still open
            edited.getImages().get(0).setCaptionEn("Chragh settlement");
            edited.getImages().get(0).setCaptionFr("Village de Tchragh");
            return null;
        }).when(settlementTranslationService).fillMissing(existing);

        SettlementEditDto dto = new SettlementEditDto();
        dto.setImages(List.of(new ImageResponseDto(
            null, new LanguagesResponseDto("Չրաղ բնակավայր", "", ""), "/images/monuments/x.jpg"
        )));

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getImages().get(0).getCaption().getHy()).isEqualTo("Չրաղ բնակավայր");
        assertThat(result.getImages().get(0).getCaption().getEn()).isEqualTo("Chragh settlement");
        assertThat(result.getImages().get(0).getCaption().getFr()).isEqualTo("Village de Tchragh");
        verify(settlementTranslationService).fillMissing(existing);
    }

    @Test
    void editSettlement_translatesAfterTheEditIsAppliedAndBeforeItIsSaved() {
        Settlement existing = stubEditOf(393L);

        settlementService.editSettlement(393L, new SettlementEditDto());

        InOrder order = inOrder(settlementTranslationService, settlementRepository);
        order.verify(settlementTranslationService).fillMissing(existing);
        order.verify(settlementRepository).save(existing);
    }

    @Test
    void editSettlement_doesNotTranslateWhenTheSettlementDoesNotExist() {
        when(settlementRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> settlementService.editSettlement(404L, new SettlementEditDto()))
            .isInstanceOf(RuntimeException.class);

        verifyNoInteractions(settlementTranslationService);
    }

    @Test
    void editSettlement_leavesStoredValuesAloneForAnOmittedGroup() {
        Settlement existing = stubEditOf(393L);
        existing.setHistoryHy("հին պատմություն");
        existing.setHistoryEn("old history");

        SettlementEditDto dto = new SettlementEditDto();
        dto.setName(group("Չրաղ"));

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getHistory().getHy()).isEqualTo("հին պատմություն");
        assertThat(result.getHistory().getEn()).isEqualTo("old history");
    }

    @Test
    void editSettlement_survivesANullDescriptionGroupComingBackFromTheFrontend() {
        stubEditOf(393L);

        SettlementEditDto dto = new SettlementEditDto();
        dto.setName(group("Չրաղ"));
        dto.setDescription(null);

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getDescription()).isNull();
    }

    @Test
    void editSettlement_storesANullEnglishOrFrenchNameAsEmptyBecauseTheColumnsAreNotNull() {
        stubEditOf(393L);

        SettlementEditDto dto = new SettlementEditDto();
        dto.setName(new LanguagesResponseDto("Չրաղ", null, null));

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getName().getHy()).isEqualTo("Չրաղ");
        assertThat(result.getName().getEn()).isEmpty();
        assertThat(result.getName().getFr()).isEmpty();
    }

    @Test
    void editSettlement_movesToAnotherRegionOnlyWhenOneIsSent() {
        Settlement existing = stubEditOf(393L);
        when(regionRepository.findById(9L)).thenReturn(Optional.of(region(9L)));

        assertThat(settlementService.editSettlement(393L, new SettlementEditDto()).getRegionId()).isEqualTo(5L);

        SettlementEditDto moved = new SettlementEditDto();
        moved.setRegionId(9L);
        assertThat(settlementService.editSettlement(393L, moved).getRegionId()).isEqualTo(9L);
        assertThat(existing.getRegion().getId()).isEqualTo(9L);
    }

    @Test
    void editSettlement_failsWhenTheRegionDoesNotExist() {
        when(settlementRepository.findById(393L)).thenReturn(Optional.of(settlement(393L, "Չրաղ", 5L)));
        when(regionRepository.findById(404L)).thenReturn(Optional.empty());

        SettlementEditDto dto = new SettlementEditDto();
        dto.setRegionId(404L);

        assertThatThrownBy(() -> settlementService.editSettlement(393L, dto))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Region not found");
        verify(settlementRepository, never()).save(any());
    }

    @Test
    void editSettlement_replacesAListThatIsSentAndKeepsOneThatIsOmitted() {
        Settlement existing = stubEditOf(393L);
        existing.getImages().add(new SettlementImage("/images/old.jpg", "հին", "old", "ancien", existing));
        existing.getMeasurements().add(new SettlementMeasurement("/images/m.jpg", "հատ", "plan", "plan", existing));
        existing.getVideos().add(new SettlementVideo("ֆիլմ", "film", "film", "https://youtu.be/a", existing));

        SettlementEditDto dto = new SettlementEditDto();
        dto.setImages(List.of());          // sent empty: removes the image
        dto.setMeasurements(null);         // omitted: keeps the measurement
        dto.setVideos(List.of(new MonumentVideoResponseDto(group("նոր ֆիլմ"), null, "https://youtu.be/b")));

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getImages()).isEmpty();
        assertThat(result.getMeasurements()).hasSize(1);
        assertThat(result.getMeasurements().get(0).getUrl()).isEqualTo("/images/m.jpg");
        assertThat(result.getVideos()).hasSize(1);
        assertThat(result.getVideos().get(0).getTitle().getHy()).isEqualTo("նոր ֆիլմ");
    }

    @Test
    void editSettlement_savesMeasurementsAndBibliographyInAllLanguages() {
        stubEditOf(393L);

        SettlementEditDto dto = new SettlementEditDto();
        dto.setMeasurements(List.of(new MeasurementResponseDto(null, group("Հատակագիծ"), "/images/m.jpg")));
        dto.setBibliography(List.of(new BibliographyResponseDto(null, group("Արցախի պատմություն"), "/files/b.pdf")));

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getMeasurements().get(0).getCaption().getEn()).isEqualTo("Հատակագիծ-en");
        assertThat(result.getBibliography().get(0).getTitle().getFr()).isEqualTo("Արցախի պատմություն-fr");
        assertThat(result.getBibliography().get(0).getUrl()).isEqualTo("/files/b.pdf");
    }

    @Test
    void editSettlement_changesTheTypeWhenOneIsSentAndKeepsItWhenOmitted() {
        Settlement existing = stubEditOf(393L);
        existing.setSettlementType(type(1L, "քաղաք"));
        when(settlementTypeRepository.findById(3L)).thenReturn(Optional.of(type(3L, "գյուղ")));

        assertThat(settlementService.editSettlement(393L, new SettlementEditDto()).getSettlementType().getId())
            .isEqualTo(1L);

        SettlementEditDto sent = new SettlementEditDto();
        sent.setSettlementTypeId(3L);
        assertThat(settlementService.editSettlement(393L, sent).getSettlementType().getId()).isEqualTo(3L);
    }

    // ---------- edit: the topographic block and its coordinates ----------

    @Test
    void editSettlement_savesCoordinatesInsideTheTopographicBlock() {
        stubEditOf(393L);

        SettlementTopographicResponseDto topographics = new SettlementTopographicResponseDto();
        topographics.setLatitude("39.9");
        topographics.setLongitude("45.9");
        topographics.setAddress(group("Հասցե"));
        SettlementEditDto dto = new SettlementEditDto();
        dto.setTopographics(topographics);

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
        assertThat(result.getTopographics().getAddress().getEn()).isEqualTo("Հասցե-en");
    }

    @Test
    void editSettlement_updatesTheExistingTopographicBlockInPlaceAndKeepsWhatWasOmitted() {
        Settlement existing = stubEditOf(393L);
        SettlementTopographic topographic = coordinates(existing, "39.9", "45.9");
        topographic.setAddressHy("հին հասցե");
        topographic.setHydrographyHy("գետ");

        SettlementTopographicResponseDto topographics = new SettlementTopographicResponseDto();
        topographics.setAddress(group("նոր հասցե"));
        // coordinates and the other groups omitted
        SettlementEditDto dto = new SettlementEditDto();
        dto.setTopographics(topographics);

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(existing.getTopographics()).isSameAs(topographic);
        assertThat(result.getTopographics().getAddress().getHy()).isEqualTo("նոր հասցե");
        assertThat(result.getTopographics().getHydrography().getHy()).isEqualTo("գետ");
        assertThat(result.getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
    }

    @Test
    void editSettlement_changesOneCoordinateAndKeepsTheOther() {
        Settlement existing = stubEditOf(393L);
        coordinates(existing, "39.9", "45.9");

        SettlementTopographicResponseDto topographics = new SettlementTopographicResponseDto();
        topographics.setLatitude("40.1");
        SettlementEditDto dto = new SettlementEditDto();
        dto.setTopographics(topographics);

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getTopographics().getLatitude()).isEqualTo("40.1");
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
    }

    @Test
    void editSettlement_clearsACoordinateOnlyWhenAnEmptyStringIsSent() {
        Settlement existing = stubEditOf(393L);
        coordinates(existing, "39.9", "45.9");

        SettlementTopographicResponseDto topographics = new SettlementTopographicResponseDto();
        topographics.setLatitude("");
        SettlementEditDto dto = new SettlementEditDto();
        dto.setTopographics(topographics);

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, dto);

        assertThat(result.getTopographics().getLatitude()).isEmpty();
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
    }

    @Test
    void editSettlement_leavesTheWholeTopographicBlockAndItsCoordinatesAloneWhenOmitted() {
        Settlement existing = stubEditOf(393L);
        coordinates(existing, "39.9", "45.9").setAddressHy("հասցե");

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, new SettlementEditDto());

        assertThat(result.getTopographics().getAddress().getHy()).isEqualTo("հասցե");
        assertThat(result.getTopographics().getLatitude()).isEqualTo("39.9");
    }

    /** A client that still sends longitude / latitude at the top level must not move the pin by accident. */
    @Test
    void editSettlement_doesNotTouchTheCoordinatesFromAnythingOutsideTheTopographicBlock() throws Exception {
        Settlement existing = stubEditOf(393L);
        coordinates(existing, "39.9", "45.9");

        SettlementEditDto stale = new com.fasterxml.jackson.databind.ObjectMapper()
            .readValue("{\"longitude\":\"1.0\",\"latitude\":\"2.0\",\"regionId\":null}", SettlementEditDto.class);

        SettlementDetailsResponseDto result = settlementService.editSettlement(393L, stale);

        assertThat(result.getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
    }

    @Test
    void editSettlement_failsWhenTheSettlementDoesNotExist() {
        when(settlementRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> settlementService.editSettlement(404L, new SettlementEditDto()))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Settlement not found");
        verify(settlementRepository, never()).save(any());
    }

    // ---------- translate ----------

    @Test
    void translate_hasTheTranslationServiceFillTheSettlementThenSavesIt() {
        Settlement existing = settlement(393L, "Չրաղ", 5L);
        when(settlementRepository.findById(393L)).thenReturn(Optional.of(existing));
        when(settlementRepository.save(existing)).thenReturn(existing);

        SettlementDetailsResponseDto result = settlementService.translate(393L, TranslationLanguage.en);

        verify(settlementTranslationService).translate(existing, TranslationLanguage.en);
        verify(settlementRepository).save(existing);
        assertThat(result.getId()).isEqualTo(393L);
    }

    @Test
    void translate_passesTheRequestedLanguageThrough() {
        Settlement existing = settlement(393L, "Չրաղ", 5L);
        when(settlementRepository.findById(393L)).thenReturn(Optional.of(existing));
        when(settlementRepository.save(existing)).thenReturn(existing);

        settlementService.translate(393L, TranslationLanguage.fr);

        verify(settlementTranslationService).translate(existing, TranslationLanguage.fr);
    }

    @Test
    void translate_failsWhenTheSettlementDoesNotExist() {
        when(settlementRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> settlementService.translate(404L, TranslationLanguage.en))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Settlement not found");
        verifyNoInteractions(settlementTranslationService);
    }

    // ---------- delete ----------

    @Test
    void deleteSettlement_removesImageAndMeasurementFilesWhenNoMonumentUsesIt() {
        Settlement existing = settlement(3L, "Չրաղ", 5L);
        existing.getImages().add(new SettlementImage("/images/x.jpg", "հ", null, null, existing));
        existing.getMeasurements().add(new SettlementMeasurement("/images/m.jpg", "հ", null, null, existing));
        when(settlementRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(monumentService.filter(any())).thenReturn(List.of());

        settlementService.deleteSettlement(3L);

        verify(fileStorageService).deleteImage("/images/x.jpg");
        verify(fileStorageService).deleteImage("/images/m.jpg");
        verify(settlementRepository).delete(existing);
    }

    @Test
    void deleteSettlement_throwsAndKeepsEverythingWhenMonumentsStillUseIt() {
        Settlement existing = settlement(3L, "Չրաղ", 5L);
        existing.getImages().add(new SettlementImage("/images/x.jpg", "հ", null, null, existing));
        when(settlementRepository.findById(3L)).thenReturn(Optional.of(existing));
        MonumentResponseDto monument = MonumentResponseDto.builder()
            .name(new LanguagesResponseDto("Գառնի", null, null))
            .build();
        when(monumentService.filter(any())).thenReturn(List.of(monument));

        assertThatThrownBy(() -> settlementService.deleteSettlement(3L))
            .isInstanceOf(SomethingWentWrongException.class)
            .hasMessageContaining("Գառնի");

        verify(fileStorageService, never()).deleteImage(any());
        verify(settlementRepository, never()).delete(any(Settlement.class));
    }
}
