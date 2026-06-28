package org.hishatakaran.backend.model;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class DescriptiveCharacteristicResponseDto {

    private final Long id;
    private final String typeArmenian;
    private final String typeEnglish;
    private final String typeFrench;
    private final String colorArmenian;
    private final String colorEnglish;
    private final String colorFrench;
    private final String buildingMaterialArmenian;
    private final String buildingMaterialEnglish;
    private final String buildingMaterialFrench;
    private final String implementationTechniqueArmenian;
    private final String implementationTechniqueEnglish;
    private final String implementationTechniqueFrench;
    private final String stateOfMonumentArmenian;
    private final String stateOfMonumentEnglish;
    private final String stateOfMonumentFrench;
    private final String valuationArmenian;
    private final String valuationEnglish;
    private final String valuationFrench;
}