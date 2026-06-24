package org.hishatakaran.backend.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@Builder
public class MonumentResponseDto {

    private final UUID id;
    private final String nameArmenian;
    private final String nameEnglish;
    private final String nameFrench;
    private final String status;
    private final String monumentTypeArmenian;
    private final String monumentTypeEnglish;
    private final String monumentTypeFrench;

    private final RegionResponseDto region;
    private final SettlementResponseDto settlement;

    private final String specialNameArmenian;
    private final String specialNameEnglish;
    private final String specialNameFrench;
    private final List<String> anotherNamesArmenian;
    private final List<String> anotherNamesEnglish;
    private final List<String> anotherNamesFrench;

    private final String historyArmenian;
    private final String historyEnglish;
    private final String historyFrench;
    private final String originalAffiliationArmenian;
    private final String originalAffiliationEnglish;
    private final String originalAffiliationFrench;
    private final String storageUnitNameArmenian;
    private final String storageUnitNameEnglish;
    private final String storageUnitNameFrench;
    private final String conditionArmenian;
    private final String conditionEnglish;
    private final String conditionFrench;

    private final List<String> pictures;

    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

    private final List<BibliographyResponseDto> bibliography;
    private final List<TopographicResponseDto> topographics;
    private final List<HistoricalReferenceResponseDto> historicalReferences;
    private final List<DescriptiveCharacteristicResponseDto> descriptiveCharacteristics;

    private final boolean showInMainPage;
}