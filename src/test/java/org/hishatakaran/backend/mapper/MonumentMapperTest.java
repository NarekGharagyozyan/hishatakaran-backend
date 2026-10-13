package org.hishatakaran.backend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentTypes;
import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.junit.jupiter.api.Test;

class MonumentMapperTest {

    private static final ZonedDateTime CREATED =
        ZonedDateTime.of(2026, 9, 17, 12, 0, 0, 0, ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED =
        ZonedDateTime.of(2026, 9, 18, 8, 30, 0, 0, ZoneOffset.UTC);

    private static Monument monument() {
        Monument monument = new Monument();
        monument.setId(1L);
        monument.setNameHy("Գառնի");
        monument.setCreatedAt(CREATED);
        monument.setUpdatedAt(UPDATED);
        monument.setBibliography(List.of());
        monument.setMonumentType(new MonumentTypes("եկեղեցի", "church", "eglise"));
        monument.setTopographics(new Topographic());
        monument.setHistoricalReferences(new HistoricalReference());
        monument.setDescriptiveCharacteristics(new DescriptiveCharacteristicReference());
        return monument;
    }

    /**
     * The frontend feeds these straight into JavaScript's Date, which takes milliseconds.
     * Emitting epoch SECONDS here renders every monument as a date in January 1970.
     */
    @Test
    void toDto_emitsTimestampsInMillisecondsNotSeconds() {
        MonumentResponseDto result = MonumentMapper.toDto(monument());

        assertThat(result.getCreatedAt()).isEqualTo(CREATED.toInstant().toEpochMilli());
        assertThat(result.getUpdatedAt()).isEqualTo(UPDATED.toInstant().toEpochMilli());

        // Read back the way the frontend does it: the original instant, not 1970.
        assertThat(Instant.ofEpochMilli(result.getCreatedAt())).isEqualTo(CREATED.toInstant());
        assertThat(Instant.ofEpochMilli(result.getUpdatedAt())).isEqualTo(UPDATED.toInstant());
        assertThat(Instant.ofEpochMilli(result.getCreatedAt()).atZone(ZoneOffset.UTC).getYear())
            .isEqualTo(2026);
    }

    @Test
    void toDto_keepsCreatedAndUpdatedDistinct() {
        MonumentResponseDto result = MonumentMapper.toDto(monument());

        assertThat(result.getUpdatedAt()).isGreaterThan(result.getCreatedAt());
    }

    /**
     * A monument embeds its settlement in the same full form as GET /settlements/{id}, so the
     * coordinates are inside the settlement's topographic block.
     */
    @Test
    void toDto_embedsTheWholeSettlementWithItsCoordinatesInsideTopographics() {
        Region region = new Region();
        region.setId(5L);
        Settlement settlement = new Settlement();
        settlement.setId(393L);
        settlement.setNameHy("Չրաղ");
        settlement.setRegion(region);
        SettlementTopographic topographic = new SettlementTopographic();
        topographic.setLatitude("39.9");
        topographic.setLongitude("45.9");
        settlement.setTopographics(topographic);
        Monument monument = monument();
        monument.setSettlement(settlement);

        MonumentResponseDto result = MonumentMapper.toDto(monument);

        assertThat(result.getSettlement()).usingRecursiveComparison().isEqualTo(SettlementMapper.toDetailsDto(settlement));
        assertThat(result.getSettlement().getName().getHy()).isEqualTo("Չրաղ");
        assertThat(result.getSettlement().getRegionId()).isEqualTo(5L);
        assertThat(result.getSettlement().getTopographics().getLatitude()).isEqualTo("39.9");
        assertThat(result.getSettlement().getTopographics().getLongitude()).isEqualTo("45.9");
    }
}
