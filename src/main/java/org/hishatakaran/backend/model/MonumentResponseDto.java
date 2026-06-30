package org.hishatakaran.backend.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@Builder
public class MonumentResponseDto {

    private final Long id;
    private final String nameHy;
    private final String nameEn;
    private final String nameFr;
    private final String status;
    private final String monumentTypeHy;
    private final String monumentTypeEn;
    private final String monumentTypeFr;

    private final RegionResponseDto region;
    private final SettlementResponseDto settlement;

    private final String specialNameHy;
    private final String specialNameEn;
    private final String specialNameFr;
    private final List<String> anotherNamesHy;
    private final List<String> anotherNamesEn;
    private final List<String> anotherNamesFr;

    private final String historyHy;
    private final String historyEn;
    private final String historyFr;
    private final String originalAffiliationHy;
    private final String originalAffiliationEn;
    private final String originalAffiliationFr;
    private final String storageUnitNameHy;
    private final String storageUnitNameEn;
    private final String storageUnitNameFr;
    private final String conditionHy;
    private final String conditionEn;
    private final String conditionFr;

    private final List<String> pictures;

    private final Long createdAt;
    private final Long updatedAt;

    private final List<BibliographyResponseDto> bibliography;
    private final TopographicResponseDto topographics;
    private final HistoricalReferenceResponseDto historicalReferences;
    private final DescriptiveCharacteristicResponseDto descriptiveCharacteristics;

    private final boolean showInMainPage;
}