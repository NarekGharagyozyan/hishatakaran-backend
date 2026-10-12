package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementBibliography;
import org.hishatakaran.backend.entity.SettlementImage;
import org.hishatakaran.backend.entity.SettlementMeasurement;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.entity.SettlementVideo;
import org.hishatakaran.backend.model.BibliographyTranslationDto;
import org.hishatakaran.backend.model.ImageAndMeasurementTranslationDto;
import org.hishatakaran.backend.model.SettlementTopographicTranslationDto;
import org.hishatakaran.backend.model.SettlementTranslationDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.model.VideoTranslationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettlementTranslationServiceTest {

    @Mock
    private GeminiService geminiService;

    @InjectMocks
    private SettlementTranslationService settlementTranslationService;

    // ---------- fixtures ----------

    /** A settlement whose Armenian side is filled and whose English and French are still empty. */
    private static Settlement armenianOnly() {
        Settlement settlement = new Settlement();
        settlement.setNameHy("Չրաղ");
        settlement.setNameEn("");
        settlement.setNameFr("");
        settlement.setAnotherNamesHy("Ջրաղ");
        settlement.setHistoryHy("պատմություն");
        settlement.setDescriptionHy("նկարագրություն");
        settlement.setDataUpdateHy("թարմացում");
        return settlement;
    }

    private static SettlementTranslationDto translation(String prefix) {
        SettlementTranslationDto dto = new SettlementTranslationDto();
        dto.setName(prefix + " name");
        dto.setAnotherNames(prefix + " another");
        dto.setHistory(prefix + " history");
        dto.setDescription(prefix + " description");
        dto.setDataUpdate(prefix + " update");
        return dto;
    }

    private static ImageAndMeasurementTranslationDto caption(String value) {
        ImageAndMeasurementTranslationDto dto = new ImageAndMeasurementTranslationDto();
        dto.setCaption(value);
        return dto;
    }

    private static VideoTranslationDto videoTitle(String value) {
        VideoTranslationDto dto = new VideoTranslationDto();
        dto.setTitle(value);
        return dto;
    }

    private static BibliographyTranslationDto bibliographyTitle(String value) {
        BibliographyTranslationDto dto = new BibliographyTranslationDto();
        dto.setTitle(value);
        return dto;
    }

    private void geminiReturns(Settlement settlement, TranslationLanguage language, SettlementTranslationDto dto) {
        when(geminiService.translateSettlement(anyMap(), eq(language))).thenReturn(dto);
    }

    // ---------- language selection ----------

    @Test
    void translate_toEnglishFillsOnlyTheEnglishColumns() {
        Settlement settlement = armenianOnly();
        geminiReturns(settlement, TranslationLanguage.en, translation("EN"));

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getNameEn()).isEqualTo("EN name");
        assertThat(settlement.getAnotherNamesEn()).isEqualTo("EN another");
        assertThat(settlement.getHistoryEn()).isEqualTo("EN history");
        assertThat(settlement.getDescriptionEn()).isEqualTo("EN description");
        assertThat(settlement.getDataUpdateEn()).isEqualTo("EN update");

        assertThat(settlement.getNameFr()).isEmpty();
        assertThat(settlement.getHistoryFr()).isNull();
        assertThat(settlement.getDescriptionFr()).isNull();
    }

    @Test
    void translate_toFrenchFillsOnlyTheFrenchColumns() {
        Settlement settlement = armenianOnly();
        geminiReturns(settlement, TranslationLanguage.fr, translation("FR"));

        settlementTranslationService.translate(settlement, TranslationLanguage.fr);

        assertThat(settlement.getNameFr()).isEqualTo("FR name");
        assertThat(settlement.getAnotherNamesFr()).isEqualTo("FR another");
        assertThat(settlement.getHistoryFr()).isEqualTo("FR history");
        assertThat(settlement.getDescriptionFr()).isEqualTo("FR description");
        assertThat(settlement.getDataUpdateFr()).isEqualTo("FR update");

        assertThat(settlement.getNameEn()).isEmpty();
        assertThat(settlement.getHistoryEn()).isNull();
    }

    @Test
    void translate_neverTouchesTheArmenianText() {
        Settlement settlement = armenianOnly();
        geminiReturns(settlement, TranslationLanguage.en, translation("EN"));

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getNameHy()).isEqualTo("Չրաղ");
        assertThat(settlement.getHistoryHy()).isEqualTo("պատմություն");
        assertThat(settlement.getDescriptionHy()).isEqualTo("նկարագրություն");
    }

    @Test
    void translate_replacesATranslationThatAlreadyExistsLikeTheMonumentButtonDoes() {
        Settlement settlement = armenianOnly();
        settlement.setHistoryEn("stale");
        geminiReturns(settlement, TranslationLanguage.en, translation("EN"));

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getHistoryEn()).isEqualTo("EN history");
    }

    // ---------- the NOT NULL name ----------

    @Test
    void translate_keepsTheExistingNameWhenGeminiReturnsAnEmptyOne() {
        Settlement settlement = armenianOnly();
        settlement.setNameEn("Chragh");
        SettlementTranslationDto dto = translation("EN");
        dto.setName("  ");
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        // name_en is NOT NULL: overwriting it with null would fail the whole save.
        assertThat(settlement.getNameEn()).isEqualTo("Chragh");
    }

    @Test
    void translate_keepsTheExistingNameWhenGeminiReturnsTheWordNull() {
        Settlement settlement = armenianOnly();
        settlement.setNameFr("Tchragh");
        SettlementTranslationDto dto = translation("FR");
        dto.setName("null");
        geminiReturns(settlement, TranslationLanguage.fr, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.fr);

        assertThat(settlement.getNameFr()).isEqualTo("Tchragh");
    }

    @Test
    void translate_turnsBlankAndTheWordNullIntoNullForTheOptionalFields() {
        Settlement settlement = armenianOnly();
        SettlementTranslationDto dto = translation("EN");
        dto.setHistory("null");
        dto.setDescription("   ");
        dto.setAnotherNames(null);
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getHistoryEn()).isNull();
        assertThat(settlement.getDescriptionEn()).isNull();
        assertThat(settlement.getAnotherNamesEn()).isNull();
    }

    @Test
    void translate_changesNothingWhenGeminiReturnsNothing() {
        Settlement settlement = armenianOnly();
        settlement.setHistoryEn("kept");
        geminiReturns(settlement, TranslationLanguage.en, null);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getHistoryEn()).isEqualTo("kept");
        assertThat(settlement.getNameEn()).isEmpty();
    }

    // ---------- topographics ----------

    @Test
    void translate_fillsTheTopographicBlockInTheRequestedLanguage() {
        Settlement settlement = armenianOnly();
        SettlementTopographic topographic = new SettlementTopographic();
        topographic.setTopographyHy("սարահարթ");
        settlement.setTopographics(topographic);

        SettlementTranslationDto dto = translation("EN");
        SettlementTopographicTranslationDto topographics = new SettlementTopographicTranslationDto();
        topographics.setTopography("plateau");
        topographics.setAddress("main street");
        topographics.setAltitude("1200 m");
        topographics.setHydrography("river");
        topographics.setDataUpdate("checked");
        dto.setTopographics(topographics);
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(topographic.getTopographyEn()).isEqualTo("plateau");
        assertThat(topographic.getAddressEn()).isEqualTo("main street");
        assertThat(topographic.getAltitudeEn()).isEqualTo("1200 m");
        assertThat(topographic.getHydrographyEn()).isEqualTo("river");
        assertThat(topographic.getDataUpdateEn()).isEqualTo("checked");
        assertThat(topographic.getTopographyFr()).isNull();
        assertThat(topographic.getTopographyHy()).isEqualTo("սարահարթ");
    }

    @Test
    void translate_neverTouchesTheCoordinates() {
        Settlement settlement = armenianOnly();
        SettlementTopographic topographic = new SettlementTopographic();
        topographic.setLatitude("39.9");
        topographic.setLongitude("45.9");
        settlement.setTopographics(topographic);

        SettlementTranslationDto dto = translation("EN");
        dto.setTopographics(new SettlementTopographicTranslationDto());
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(topographic.getLatitude()).isEqualTo("39.9");
        assertThat(topographic.getLongitude()).isEqualTo("45.9");
    }

    @Test
    void translate_storesTheHtmlOfAnUpdatedDataSectionAsGeminiReturnedIt() {
        Settlement settlement = armenianOnly();
        SettlementTranslationDto dto = translation("EN");
        dto.setDataUpdate("<p>Updated</p><a href=\"https://example.org/x\">link</a><img src=\"/images/monuments/x.jpg\">");
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getDataUpdateEn())
            .isEqualTo("<p>Updated</p><a href=\"https://example.org/x\">link</a><img src=\"/images/monuments/x.jpg\">");
    }

    @Test
    void translate_toFrenchFillsTheFrenchTopographicColumns() {
        Settlement settlement = armenianOnly();
        SettlementTopographic topographic = new SettlementTopographic();
        settlement.setTopographics(topographic);

        SettlementTranslationDto dto = translation("FR");
        SettlementTopographicTranslationDto topographics = new SettlementTopographicTranslationDto();
        topographics.setTopography("plateau");
        topographics.setHydrography("rivière");
        dto.setTopographics(topographics);
        geminiReturns(settlement, TranslationLanguage.fr, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.fr);

        assertThat(topographic.getTopographyFr()).isEqualTo("plateau");
        assertThat(topographic.getHydrographyFr()).isEqualTo("rivière");
        assertThat(topographic.getTopographyEn()).isNull();
    }

    @Test
    void translate_ignoresATopographicAnswerWhenTheSettlementHasNoTopographicBlock() {
        Settlement settlement = armenianOnly();
        SettlementTranslationDto dto = translation("EN");
        SettlementTopographicTranslationDto topographics = new SettlementTopographicTranslationDto();
        topographics.setTopography("plateau");
        dto.setTopographics(topographics);
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getTopographics()).isNull();
        assertThat(settlement.getHistoryEn()).isEqualTo("EN history");
    }

    // ---------- aligned lists ----------

    @Test
    void translate_appliesImageAndMeasurementCaptionsByPosition() {
        Settlement settlement = armenianOnly();
        settlement.getImages().add(new SettlementImage("/a.jpg", "առաջին", null, null, settlement));
        settlement.getImages().add(new SettlementImage("/b.jpg", "երկրորդ", null, null, settlement));
        settlement.getMeasurements().add(new SettlementMeasurement("/m.jpg", "հատակագիծ", null, null, settlement));

        SettlementTranslationDto dto = translation("EN");
        dto.setImages(List.of(caption("first"), caption("second")));
        dto.setMeasurements(List.of(caption("plan")));
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getImages().get(0).getCaptionEn()).isEqualTo("first");
        assertThat(settlement.getImages().get(1).getCaptionEn()).isEqualTo("second");
        assertThat(settlement.getImages().get(0).getCaptionFr()).isNull();
        assertThat(settlement.getImages().get(0).getCaptionHy()).isEqualTo("առաջին");
        assertThat(settlement.getMeasurements().get(0).getCaptionEn()).isEqualTo("plan");
    }

    @Test
    void translate_appliesVideoAndBibliographyTitlesByPositionInFrench() {
        Settlement settlement = armenianOnly();
        settlement.getVideos().add(new SettlementVideo("ֆիլմ", null, null, "https://youtu.be/a", settlement));
        settlement.getBibliography().add(new SettlementBibliography("գիրք", null, null, "/b.pdf", settlement));

        SettlementTranslationDto dto = translation("FR");
        dto.setVideos(List.of(videoTitle("film")));
        dto.setBibliography(List.of(bibliographyTitle("livre")));
        geminiReturns(settlement, TranslationLanguage.fr, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.fr);

        assertThat(settlement.getVideos().get(0).getTitleFr()).isEqualTo("film");
        assertThat(settlement.getVideos().get(0).getTitleEn()).isNull();
        assertThat(settlement.getBibliography().get(0).getTitleFr()).isEqualTo("livre");
        assertThat(settlement.getBibliography().get(0).getUrl()).isEqualTo("/b.pdf");
    }

    @Test
    void translate_appliesAShortListAsFarAsItGoesAndKeepsTheRest() {
        Settlement settlement = armenianOnly();
        settlement.getImages().add(new SettlementImage("/a.jpg", "առաջին", null, null, settlement));
        settlement.getImages().add(new SettlementImage("/b.jpg", "երկրորդ", "kept", null, settlement));

        SettlementTranslationDto dto = translation("EN");
        dto.setImages(List.of(caption("first")));
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getImages().get(0).getCaptionEn()).isEqualTo("first");
        assertThat(settlement.getImages().get(1).getCaptionEn()).isEqualTo("kept");
    }

    @Test
    void translate_ignoresAnExtraEntryGeminiMadeUp() {
        Settlement settlement = armenianOnly();
        settlement.getImages().add(new SettlementImage("/a.jpg", "առաջին", null, null, settlement));

        SettlementTranslationDto dto = translation("EN");
        dto.setImages(List.of(caption("first"), caption("invented")));
        geminiReturns(settlement, TranslationLanguage.en, dto);

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getImages()).hasSize(1);
        assertThat(settlement.getImages().get(0).getCaptionEn()).isEqualTo("first");
    }

    @Test
    void translate_skipsListsGeminiOmitted() {
        Settlement settlement = armenianOnly();
        settlement.getImages().add(new SettlementImage("/a.jpg", "առաջին", "kept", null, settlement));
        geminiReturns(settlement, TranslationLanguage.en, translation("EN"));   // no lists in the answer

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        assertThat(settlement.getImages().get(0).getCaptionEn()).isEqualTo("kept");
    }

    // ---------- the translate button sends and replaces everything ----------

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Map<String, Object> sentTo(TranslationLanguage language, int calls) {
        ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass((Class) Map.class);
        verify(geminiService, times(calls)).translateSettlement(captor.capture(), eq(language));
        return captor.getValue();
    }

    @Test
    void translate_sendsEveryArmenianFieldEvenWhereATranslationAlreadyExists() {
        Settlement settlement = armenianOnly();
        settlement.setHistoryEn("already translated");
        settlement.getImages().add(new SettlementImage("/a.jpg", "առաջին", "first", "premier", settlement));
        geminiReturns(settlement, TranslationLanguage.en, translation("EN"));

        settlementTranslationService.translate(settlement, TranslationLanguage.en);

        Map<String, Object> source = sentTo(TranslationLanguage.en, 1);
        assertThat(source.get("history")).isEqualTo("պատմություն");
        assertThat(source.get("name")).isEqualTo("Չրաղ");
        assertThat(source.get("images")).isEqualTo(List.of(Map.of("caption", "առաջին")));
    }

    // ---------- filling the gaps when an edit is saved ----------

    /** Fully translated settlement; a test then blanks the one thing it is about. */
    private static Settlement translated() {
        Settlement settlement = new Settlement();
        settlement.setId(7L);
        settlement.setNameHy("Չրաղ");
        settlement.setNameEn("Chragh");
        settlement.setNameFr("Tchragh");
        settlement.setHistoryHy("պատմություն");
        settlement.setHistoryEn("history");
        settlement.setHistoryFr("histoire");
        return settlement;
    }

    private void geminiReturnsForBoth(SettlementTranslationDto en, SettlementTranslationDto fr) {
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.en))).thenReturn(en);
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.fr))).thenReturn(fr);
    }

    @Test
    void fillMissing_translatesAFieldThatHasOnlyArmenianIntoBothLanguages() {
        Settlement settlement = translated();
        settlement.setDescriptionHy("նկարագրություն");     // the field the admin just added
        SettlementTranslationDto en = new SettlementTranslationDto();
        en.setDescription("EN description");
        SettlementTranslationDto fr = new SettlementTranslationDto();
        fr.setDescription("FR description");
        geminiReturnsForBoth(en, fr);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getDescriptionEn()).isEqualTo("EN description");
        assertThat(settlement.getDescriptionFr()).isEqualTo("FR description");
        assertThat(settlement.getDescriptionHy()).isEqualTo("նկարագրություն");
    }

    @Test
    void fillMissing_sendsOnlyTheGapsAndNullsEverythingElse() {
        Settlement settlement = translated();
        settlement.setDescriptionHy("նկարագրություն");
        geminiReturnsForBoth(new SettlementTranslationDto(), new SettlementTranslationDto());

        settlementTranslationService.fillMissing(settlement);

        Map<String, Object> source = sentTo(TranslationLanguage.en, 1);
        assertThat(source.get("description")).isEqualTo("նկարագրություն");
        assertThat(source.get("name")).isNull();            // complete: not sent
        assertThat(source.get("history")).isNull();         // complete: not sent
        assertThat(source.get("topographics")).isNull();    // none at all
        assertThat(source.get("images")).isEqualTo(List.of());
    }

    @Test
    void fillMissing_translatesOnlyTheLanguageThatIsMissing() {
        Settlement settlement = translated();
        settlement.setDescriptionHy("նկարագրություն");
        settlement.setDescriptionEn("typed by hand");        // only French is missing
        SettlementTranslationDto fr = new SettlementTranslationDto();
        fr.setDescription("FR description");
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.fr))).thenReturn(fr);

        settlementTranslationService.fillMissing(settlement);

        verify(geminiService, never()).translateSettlement(anyMap(), eq(TranslationLanguage.en));
        assertThat(settlement.getDescriptionEn()).isEqualTo("typed by hand");
        assertThat(settlement.getDescriptionFr()).isEqualTo("FR description");
    }

    @Test
    void fillMissing_neverReplacesATranslationThatIsAlreadyThere() {
        Settlement settlement = translated();
        settlement.setDescriptionHy("նկարագրություն");
        SettlementTranslationDto en = new SettlementTranslationDto();
        en.setName("EN other name");                         // Gemini volunteers a different name and history
        en.setHistory("EN other history");
        en.setDescription("EN description");
        SettlementTranslationDto fr = new SettlementTranslationDto();
        fr.setName("FR other name");
        fr.setHistory("FR other history");
        fr.setDescription("FR description");
        geminiReturnsForBoth(en, fr);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getNameEn()).isEqualTo("Chragh");
        assertThat(settlement.getNameFr()).isEqualTo("Tchragh");
        assertThat(settlement.getHistoryEn()).isEqualTo("history");
        assertThat(settlement.getHistoryFr()).isEqualTo("histoire");
        assertThat(settlement.getDescriptionEn()).isEqualTo("EN description");
    }

    @Test
    void fillMissing_makesNoGeminiCallWhenNothingIsMissing() {
        settlementTranslationService.fillMissing(translated());

        verifyNoInteractions(geminiService);
    }

    @Test
    void fillMissing_treatsBlankAndTheWordNullAsEmpty() {
        Settlement settlement = translated();
        settlement.setHistoryEn("   ");
        settlement.setHistoryFr("null");
        SettlementTranslationDto en = new SettlementTranslationDto();
        en.setHistory("EN history");
        SettlementTranslationDto fr = new SettlementTranslationDto();
        fr.setHistory("FR history");
        geminiReturnsForBoth(en, fr);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getHistoryEn()).isEqualTo("EN history");
        assertThat(settlement.getHistoryFr()).isEqualTo("FR history");
    }

    @Test
    void fillMissing_neverMakesUpTextForAFieldThatHasNoArmenian() {
        Settlement settlement = translated();
        settlement.setHistoryEn(null);                       // the gap that triggers the call
        settlement.setDescriptionHy(null);                   // no Armenian here: nothing to translate
        SettlementTranslationDto en = new SettlementTranslationDto();
        en.setHistory("EN history");
        en.setDescription("invented description");
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.en))).thenReturn(en);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getHistoryEn()).isEqualTo("EN history");
        assertThat(settlement.getDescriptionEn()).isNull();
    }

    @Test
    void fillMissing_keepsListPositionsAlignedByNullingEntriesThatAreComplete() {
        Settlement settlement = translated();
        settlement.getImages().add(new SettlementImage("/a.jpg", "առաջին", "first", "premier", settlement));
        settlement.getImages().add(new SettlementImage("/b.jpg", "երկրորդ", null, "deuxième", settlement));  // the new caption
        SettlementTranslationDto en = new SettlementTranslationDto();
        en.setImages(List.of(caption("must not replace"), caption("second")));
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.en))).thenReturn(en);

        settlementTranslationService.fillMissing(settlement);

        Map<String, Object> source = sentTo(TranslationLanguage.en, 1);
        assertThat(source.get("images")).isEqualTo(java.util.Arrays.asList(
            java.util.Collections.singletonMap("caption", null),
            Map.of("caption", "երկրորդ")
        ));
        assertThat(settlement.getImages().get(0).getCaptionEn()).isEqualTo("first");
        assertThat(settlement.getImages().get(1).getCaptionEn()).isEqualTo("second");
        assertThat(settlement.getImages().get(1).getCaptionFr()).isEqualTo("deuxième");
    }

    @Test
    void fillMissing_fillsEveryKindOfListEntryAndTheTopographicBlock() {
        Settlement settlement = translated();
        settlement.getBibliography().add(new SettlementBibliography("գիրք", null, null, "/b.pdf", settlement));
        settlement.getMeasurements().add(new SettlementMeasurement("/m.jpg", "հատակագիծ", null, null, settlement));
        settlement.getVideos().add(new SettlementVideo("ֆիլմ", null, null, "https://youtu.be/a", settlement));
        SettlementTopographic topographic = new SettlementTopographic();
        topographic.setAddressHy("հասցե");
        topographic.setLatitude("39.9");
        topographic.setLongitude("45.9");
        settlement.setTopographics(topographic);

        SettlementTranslationDto en = new SettlementTranslationDto();
        en.setBibliography(List.of(bibliographyTitle("EN book")));
        en.setMeasurements(List.of(caption("EN plan")));
        en.setVideos(List.of(videoTitle("EN film")));
        SettlementTopographicTranslationDto enTopographic = new SettlementTopographicTranslationDto();
        enTopographic.setAddress("EN address");
        en.setTopographics(enTopographic);
        SettlementTranslationDto fr = new SettlementTranslationDto();
        fr.setBibliography(List.of(bibliographyTitle("FR livre")));
        fr.setMeasurements(List.of(caption("FR plan")));
        fr.setVideos(List.of(videoTitle("FR film")));
        SettlementTopographicTranslationDto frTopographic = new SettlementTopographicTranslationDto();
        frTopographic.setAddress("FR adresse");
        fr.setTopographics(frTopographic);
        geminiReturnsForBoth(en, fr);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getBibliography().get(0).getTitleEn()).isEqualTo("EN book");
        assertThat(settlement.getBibliography().get(0).getTitleFr()).isEqualTo("FR livre");
        assertThat(settlement.getMeasurements().get(0).getCaptionEn()).isEqualTo("EN plan");
        assertThat(settlement.getMeasurements().get(0).getCaptionFr()).isEqualTo("FR plan");
        assertThat(settlement.getVideos().get(0).getTitleEn()).isEqualTo("EN film");
        assertThat(settlement.getVideos().get(0).getTitleFr()).isEqualTo("FR film");
        assertThat(topographic.getAddressEn()).isEqualTo("EN address");
        assertThat(topographic.getAddressFr()).isEqualTo("FR adresse");
        assertThat(topographic.getLatitude()).isEqualTo("39.9");
        assertThat(topographic.getLongitude()).isEqualTo("45.9");
    }

    @Test
    void fillMissing_keepsTheNotNullNameWhenGeminiReturnsNothingForIt() {
        Settlement settlement = translated();
        settlement.setNameEn("");                            // a name just created, not translated yet
        SettlementTranslationDto en = new SettlementTranslationDto();
        en.setName(null);
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.en))).thenReturn(en);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getNameEn()).isEmpty();
    }

    @Test
    void fillMissing_aFailedTranslationIsSwallowedSoTheEditIsNotLost() {
        Settlement settlement = translated();
        settlement.setDescriptionHy("նկարագրություն");
        when(geminiService.translateSettlement(anyMap(), any(TranslationLanguage.class)))
            .thenThrow(new RuntimeException("Gemini is down"));

        settlementTranslationService.fillMissing(settlement);     // must not throw

        assertThat(settlement.getDescriptionEn()).isNull();
        assertThat(settlement.getDescriptionFr()).isNull();
        assertThat(settlement.getDescriptionHy()).isEqualTo("նկարագրություն");
    }

    @Test
    void fillMissing_oneLanguageFailingDoesNotStopTheOther() {
        Settlement settlement = translated();
        settlement.setDescriptionHy("նկարագրություն");
        SettlementTranslationDto fr = new SettlementTranslationDto();
        fr.setDescription("FR description");
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.en)))
            .thenThrow(new RuntimeException("quota"));
        when(geminiService.translateSettlement(anyMap(), eq(TranslationLanguage.fr))).thenReturn(fr);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getDescriptionEn()).isNull();
        assertThat(settlement.getDescriptionFr()).isEqualTo("FR description");
    }

    @Test
    void fillMissing_doesNothingWhenGeminiReturnsNothing() {
        Settlement settlement = translated();
        settlement.setDescriptionHy("նկարագրություն");
        when(geminiService.translateSettlement(anyMap(), any(TranslationLanguage.class))).thenReturn(null);

        settlementTranslationService.fillMissing(settlement);

        assertThat(settlement.getDescriptionEn()).isNull();
        assertThat(settlement.getDescriptionFr()).isNull();
    }
}
