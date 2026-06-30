package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class DescriptiveCharacteristicResponseDto {

    private final Long id;
    private final String typeHy;
    private final String typeEn;
    private final String typeFr;
    private final String colorHy;
    private final String colorEn;
    private final String colorFr;
    private final String buildingMaterialHy;
    private final String buildingMaterialEn;
    private final String buildingMaterialFr;
    private final String implementationTechniqueHy;
    private final String implementationTechniqueEn;
    private final String implementationTechniqueFr;
    private final String stateOfMonumentHy;
    private final String stateOfMonumentEn;
    private final String stateOfMonumentFr;
    private final String valuationHy;
    private final String valuationEn;
    private final String valuationFr;
}