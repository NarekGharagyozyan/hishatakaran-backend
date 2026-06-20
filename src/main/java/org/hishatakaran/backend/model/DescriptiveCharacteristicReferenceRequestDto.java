package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class DescriptiveCharacteristicReferenceRequestDto {

  private String theBuildingMaterial;
  private String type;
  private String color;
  private String implementationTechnique;
  private String stateOfMonument;
  private String valuation;

  @Override
  public String toString() {
    return "DescriptiveCharacteristicReferenceRequestDto{" +
        "theBuildingMaterial='" + theBuildingMaterial + '\'' +
        ", type='" + type + '\'' +
        ", color='" + color + '\'' +
        ", implementationTechnique='" + implementationTechnique + '\'' +
        ", stateOfMonument='" + stateOfMonument + '\'' +
        ", valuation='" + valuation + '\'' +
        '}';
  }
}
