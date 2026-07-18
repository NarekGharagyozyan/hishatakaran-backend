package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class DescriptiveCharacteristicReferenceRequestDto {

  private String archeologicalOverviewStratigraphyFindings;
  private String architecturalOverview;
  private String decorativeAndMonumentalFeaturesCompositionColours;
  private String openingsWindows;
  private String openingsEntrances;
  private String constructions;
  private String roof;
  private String theBuildingMaterial;
  private String levelsOfConstruction;
  private String type;
  private String exterior;
  private String implementationTechnique;
  private String length;
  private String width;
  private String height;
  private String depthThickness;
  private String area;
  private String lengthOfSpan;
  private String stateOfMonument;
  private String valuation;

  @Override
  public String toString() {
    return "DescriptiveCharacteristicReferenceRequestDto{" +
        "archeologicalOverviewStratigraphyFindings='" + archeologicalOverviewStratigraphyFindings + '\'' +
        ", architecturalOverview='" + architecturalOverview + '\'' +
        ", decorativeAndMonumentalFeaturesCompositionColours='" + decorativeAndMonumentalFeaturesCompositionColours + '\'' +
        ", openingsWindows='" + openingsWindows + '\'' +
        ", openingsEntrances='" + openingsEntrances + '\'' +
        ", constructions='" + constructions + '\'' +
        ", roof='" + roof + '\'' +
        ", theBuildingMaterial='" + theBuildingMaterial + '\'' +
        ", levelsOfConstruction='" + levelsOfConstruction + '\'' +
        ", type='" + type + '\'' +
        ", exterior='" + exterior + '\'' +
        ", implementationTechnique='" + implementationTechnique + '\'' +
        ", length='" + length + '\'' +
        ", width='" + width + '\'' +
        ", height='" + height + '\'' +
        ", depthThickness='" + depthThickness + '\'' +
        ", area='" + area + '\'' +
        ", lengthOfSpan='" + lengthOfSpan + '\'' +
        ", stateOfMonument='" + stateOfMonument + '\'' +
        ", valuation='" + valuation + '\'' +
        '}';
  }
}
