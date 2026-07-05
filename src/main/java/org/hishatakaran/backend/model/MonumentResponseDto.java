package org.hishatakaran.backend.model;

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
    private final LanguagesResponseDto name;
    private final String status;
    private final LanguagesResponseDto monumentType;

    private final RegionResponseDto region;
    private final SettlementResponseDto settlement;

    private final LanguagesResponseDto specialName;
    private final LanguagesResponseDto anotherNames;

    private final LanguagesResponseDto history;
    private final LanguagesResponseDto originalAffiliation;
    private final LanguagesResponseDto storageUnitName;
    private final LanguagesResponseDto condition;

    private final List<String> images;
    private final List<MonumentVideoResponseDto> videos;
    private final List<String> measurements;

    private final Long createdAt;
    private final Long updatedAt;

    private final List<BibliographyResponseDto> bibliography;
    private final TopographicResponseDto topographics;
    private final HistoricalReferenceResponseDto historicalReferences;
    private final DescriptiveCharacteristicResponseDto descriptiveCharacteristics;

    private final boolean showInMainPage;
}