package org.hishatakaran.backend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementBibliography;
import org.hishatakaran.backend.entity.SettlementImage;
import org.hishatakaran.backend.entity.SettlementMeasurement;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.entity.SettlementType;
import org.hishatakaran.backend.entity.SettlementVideo;
import org.hishatakaran.backend.model.SettlementDetailsResponseDto;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class SettlementMapperTest {

    private static Settlement dossier() {
        Region region = new Region();
        region.setId(5L);

        SettlementType village = new SettlementType("գյուղ", "Village", "Village");
        village.setId(3L);

        Settlement settlement = new Settlement();
        settlement.setId(393L);
        settlement.setRegion(region);
        settlement.setSettlementType(village);
        settlement.setNameHy("Չրաղ");
        settlement.setNameEn("Chragh");
        settlement.setNameFr("Tchragh");
        settlement.setAnotherNamesHy("Ջրաղ");
        settlement.setHistoryHy("պատմություն");
        settlement.setHistoryFr("histoire");
        settlement.setDescriptionHy("նկարագրություն");
        settlement.setDataUpdateEn("updated");

        SettlementTopographic topographic = new SettlementTopographic();
        topographic.setId(7L);
        topographic.setSettlement(settlement);
        topographic.setTopographyHy("սարահարթ");
        topographic.setAddressEn("main street");
        topographic.setLatitude("39.9");
        topographic.setLongitude("45.9");
        topographic.setAltitudeFr("1200 m");
        topographic.setHydrographyHy("գետ");
        topographic.setDataUpdateHy("ստուգված");
        settlement.setTopographics(topographic);

        settlement.getImages().add(new SettlementImage("/images/x.jpg", "լուսանկար", "photo", null, settlement));
        settlement.getMeasurements().add(new SettlementMeasurement("/images/m.jpg", "հատակագիծ", "plan", "plan", settlement));
        settlement.getVideos().add(new SettlementVideo("ֆիլմ", "film", null, "https://youtu.be/dQw4w9WgXcQ", settlement));
        settlement.getBibliography().add(new SettlementBibliography("գիրք", null, "livre", "/files/b.pdf", settlement));

        return settlement;
    }

    // ---------- the whole settlement ----------

    @Test
    void toDetailsDto_mapsEveryTextGroupInAllThreeLanguages() {
        SettlementDetailsResponseDto result = SettlementMapper.toDetailsDto(dossier());

        assertThat(result.getId()).isEqualTo(393L);
        assertThat(result.getRegionId()).isEqualTo(5L);
        assertThat(result.getName().getFr()).isEqualTo("Tchragh");
        assertThat(result.getAnotherNames().getHy()).isEqualTo("Ջրաղ");
        assertThat(result.getHistory().getFr()).isEqualTo("histoire");
        assertThat(result.getDescription().getHy()).isEqualTo("նկարագրություն");
        assertThat(result.getDataUpdate().getEn()).isEqualTo("updated");
    }

    @Test
    void toDetailsDto_mapsTheTypeThatTheAdminPickedFromTheDropdown() {
        SettlementDetailsResponseDto result = SettlementMapper.toDetailsDto(dossier());

        assertThat(result.getSettlementType().getId()).isEqualTo(3L);
        assertThat(result.getSettlementType().getName().getHy()).isEqualTo("գյուղ");
        assertThat(result.getSettlementType().getName().getEn()).isEqualTo("Village");
    }

    @Test
    void toDetailsDto_givesANullTypeForASettlementThatHasNoneYet() {
        Settlement settlement = dossier();
        settlement.setSettlementType(null);

        assertThat(SettlementMapper.toDetailsDto(settlement).getSettlementType()).isNull();
    }

    @Test
    void toDetailsDto_putsTheCoordinatesInsideTheTopographicBlock() {
        SettlementDetailsResponseDto result = SettlementMapper.toDetailsDto(dossier());

        assertThat(result.getTopographics().getId()).isEqualTo(7L);
        assertThat(result.getTopographics().getTopography().getHy()).isEqualTo("սարահարթ");
        assertThat(result.getTopographics().getAddress().getEn()).isEqualTo("main street");
        assertThat(result.getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.getTopographics().getLongitude()).isEqualTo("45.9");
        assertThat(result.getTopographics().getAltitude().getFr()).isEqualTo("1200 m");
        assertThat(result.getTopographics().getHydrography().getHy()).isEqualTo("գետ");
        assertThat(result.getTopographics().getDataUpdate().getHy()).isEqualTo("ստուգված");
    }

    /** What the frontend actually receives: the pin lives under topographics, not next to the name. */
    @Test
    void toDetailsDto_serialisesCoordinatesUnderTopographicsAndNotAtTheTopLevel() {
        JsonNode json = new ObjectMapper().valueToTree(SettlementMapper.toDetailsDto(dossier()));

        assertThat(json.has("latitude")).isFalse();
        assertThat(json.has("longitude")).isFalse();
        assertThat(json.get("topographics").get("latitude").asText()).isEqualTo("39.9");
        assertThat(json.get("topographics").get("longitude").asText()).isEqualTo("45.9");
    }

    @Test
    void toDetailsDto_listsTheSectionsInTheOrderOfTheAdminForm() {
        JsonNode json = new ObjectMapper().valueToTree(SettlementMapper.toDetailsDto(dossier()));

        assertThat(json.fieldNames()).toIterable().containsExactly(
            "id", "regionId", "name", "settlementType", "anotherNames", "history", "description",
            "dataUpdate", "topographics", "bibliography", "images", "measurements", "videos"
        );
    }

    @Test
    void toDetailsDto_givesNullTopographicsWhenThereIsNoBlock() {
        Settlement settlement = dossier();
        settlement.setTopographics(null);

        assertThat(SettlementMapper.toDetailsDto(settlement).getTopographics()).isNull();
    }

    @Test
    void toDetailsDto_mapsBibliographyImagesMeasurementsAndVideos() {
        SettlementDetailsResponseDto result = SettlementMapper.toDetailsDto(dossier());

        assertThat(result.getBibliography()).hasSize(1);
        assertThat(result.getBibliography().get(0).getUrl()).isEqualTo("/files/b.pdf");
        assertThat(result.getBibliography().get(0).getTitle().getFr()).isEqualTo("livre");

        assertThat(result.getImages()).hasSize(1);
        assertThat(result.getImages().get(0).getUrl()).isEqualTo("/images/x.jpg");
        assertThat(result.getImages().get(0).getCaption().getEn()).isEqualTo("photo");

        assertThat(result.getMeasurements()).hasSize(1);
        assertThat(result.getMeasurements().get(0).getUrl()).isEqualTo("/images/m.jpg");
        assertThat(result.getMeasurements().get(0).getCaption().getFr()).isEqualTo("plan");

        assertThat(result.getVideos()).hasSize(1);
        assertThat(result.getVideos().get(0).getUrl()).isEqualTo("https://youtu.be/dQw4w9WgXcQ");
        assertThat(result.getVideos().get(0).getId()).isEqualTo("dQw4w9WgXcQ");
        assertThat(result.getVideos().get(0).getTitle().getEn()).isEqualTo("film");
    }

    @Test
    void toDetailsDto_givesNullForAnAllEmptyGroupLikeEveryOtherMapper() {
        Settlement settlement = dossier();
        settlement.setAnotherNamesHy(null);

        assertThat(SettlementMapper.toDetailsDto(settlement).getAnotherNames()).isNull();
    }

    @Test
    void toDetailsDto_returnsNullForNull() {
        assertThat(SettlementMapper.toDetailsDto(null)).isNull();
    }

    // ---------- the compact form lists, monuments and search results embed ----------

    @Test
    void toDto_carriesTheFieldsTheMapperAlwaysReturnedWithCoordinatesReadFromTheTopographicBlock() {
        SettlementResponseDto result = SettlementMapper.toDto(dossier());

        assertThat(result.getId()).isEqualTo(393L);
        assertThat(result.getName().getHy()).isEqualTo("Չրաղ");
        assertThat(result.getDescription().getHy()).isEqualTo("նկարագրություն");
        assertThat(result.getLongitude()).isEqualTo("45.9");
        assertThat(result.getLatitude()).isEqualTo("39.9");
        assertThat(result.getImages()).hasSize(1);
        assertThat(result.getRegionId()).isEqualTo(5L);
    }

    /** Lists, monuments and search results are consumed by code that predates the dossier; its shape must not move. */
    @Test
    void toDto_keepsExactlyTheSevenFieldsItHadBeforeTheDossierExisted() {
        assertThat(Arrays.stream(SettlementResponseDto.class.getDeclaredFields()).map(Field::getName))
            .containsExactly("id", "name", "description", "longitude", "latitude", "images", "regionId");
    }

    @Test
    void toDto_givesNullCoordinatesForASettlementWithoutATopographicBlock() {
        Settlement settlement = dossier();
        settlement.setTopographics(null);

        SettlementResponseDto result = SettlementMapper.toDto(settlement);

        assertThat(result.getLatitude()).isNull();
        assertThat(result.getLongitude()).isNull();
    }

    @Test
    void toDto_toleratesASettlementWithoutARegion() {
        Settlement settlement = dossier();
        settlement.setRegion(null);

        assertThat(SettlementMapper.toDto(settlement).getRegionId()).isNull();
    }

    @Test
    void toDto_returnsNullForNull() {
        assertThat(SettlementMapper.toDto(null)).isNull();
    }
}
