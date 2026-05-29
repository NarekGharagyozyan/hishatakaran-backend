package org.hishatakaran.backend.model;

import java.time.LocalDateTime;
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
    private final String name;
    private final String status;
    private final String monumentType;

    private final RegionResponseDto region;

    private final SettlementResponseDto settlement;

    private final String specialName;
    private final List<String> anotherNames;

    private final String history;
    private final String originalAffiliation;
    private final String storageUnitName;
    private final String condition;

    private final List<String> pictures;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final List<BibliographyResponseDto> bibliography;
    private final List<TopographicResponseDto> topographics;
    private final List<HistoricalReferenceResponseDto> historicalReferences;
    private final List<DescriptiveCharacteristicResponseDto> descriptiveCharacteristics;


}