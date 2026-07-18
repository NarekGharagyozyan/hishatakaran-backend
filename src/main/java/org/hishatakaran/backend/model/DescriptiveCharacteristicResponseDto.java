package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class DescriptiveCharacteristicResponseDto {

    private final Long id;
    private final LanguagesResponseDto archeologicalOverviewStratigraphyFindings;
    private final LanguagesResponseDto architecturalOverview;
    private final LanguagesResponseDto decorativeAndMonumentalFeaturesCompositionColours;
    private final LanguagesResponseDto openingsWindows;
    private final LanguagesResponseDto openingsEntrances;
    private final LanguagesResponseDto constructions;
    private final LanguagesResponseDto roof;
    private final LanguagesResponseDto type;
    private final LanguagesResponseDto levelsOfConstruction;
    private final LanguagesResponseDto buildingMaterial;
    private final LanguagesResponseDto exterior;
    private final LanguagesResponseDto implementationTechnique;
    private final LanguagesResponseDto length;
    private final LanguagesResponseDto width;
    private final LanguagesResponseDto height;
    private final LanguagesResponseDto depthThickness;
    private final LanguagesResponseDto area;
    private final LanguagesResponseDto lengthOfSpan;
    private final LanguagesResponseDto stateOfMonument;
    private final LanguagesResponseDto valuation;
}