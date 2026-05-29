package org.hishatakaran.backend.model;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class DescriptiveCharacteristicResponseDto {

    private final UUID id;
    private final String type;
    private final String color;
    private final String buildingMaterial;
    private final String implementationTechnique;
    private final String stateOfMonument;
    private final String valuation;
}