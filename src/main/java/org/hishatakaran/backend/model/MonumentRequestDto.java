package org.hishatakaran.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonumentRequestDto {

  private String name;
  private Long regionId;
  private Long settlementId;
  private Long monumentTypeId;
  private String specialName;
  private String anotherNames;
  private String history;

  private String originalAffiliation;
  private String storageUnitName;
  private String condition;

  private List<String> images;
  private List<MonumentVideoRequestDto> videos;
  private List<String> measurements;

  private List<BibliographyRequestDto> bibliography;
  private TopographicRequestDto topographics;
  private HistoricalReferenceRequestDto historicalReferences;
  private DescriptiveCharacteristicReferenceRequestDto descriptiveCharacteristics;

  private List<FootnoteRequestDto> footnotes;

  private Boolean showInMainPage;
  private String signature;

  @Override
  public String toString() {
    return "MonumentRequestDto{" +
        "name='" + name + '\'' +
        ", regionId=" + regionId +
        ", settlementId=" + settlementId +
        ", monumentTypeId='" + monumentTypeId + '\'' +
        ", specialName='" + specialName + '\'' +
        ", anotherNames='" + anotherNames + '\'' +
        ", history='" + history + '\'' +
        ", originalAffiliation='" + originalAffiliation + '\'' +
        ", storageUnitName='" + storageUnitName + '\'' +
        ", condition='" + condition + '\'' +
        ", images=" + images +
        ", videos=" + videos +
        ", measurements=" + measurements +
        ", bibliography=" + bibliography +
        ", topographics=" + topographics +
        ", historicalReferences=" + historicalReferences +
        ", descriptiveCharacteristics=" + descriptiveCharacteristics +
        ", showInMainPage=" + showInMainPage +
        ", signature='" + signature + '\'' +
        '}';
  }
}
