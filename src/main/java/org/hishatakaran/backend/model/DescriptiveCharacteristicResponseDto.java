package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class DescriptiveCharacteristicResponseDto {

    private final Long id;
    private final LanguagesResponseDto openingsEntrances;
    private final LanguagesResponseDto constructions;
    private final LanguagesResponseDto roof;
    private final LanguagesResponseDto type;
    private final LanguagesResponseDto color;
    private final LanguagesResponseDto buildingMaterial;
    private final LanguagesResponseDto implementationTechnique;
    private final LanguagesResponseDto stateOfMonument;
    private final LanguagesResponseDto valuation;
}