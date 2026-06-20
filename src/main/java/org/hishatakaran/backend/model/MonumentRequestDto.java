package org.hishatakaran.backend.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MonumentRequestDto {

  private final String name;
  private final Integer regionId;
  private final Integer settlementId;
  private final String monumentType;
  private final String specialName;
  private final List<String> anotherNames;
  private final String history;

  private final String originalAffiliation;
  private final String storageUnitName;
  private final String condition;

  private final List<MultipartFile> pictures;

  private final List<BibliographyRequestDto> bibliography;
  private final List<TopographicRequestDto> topographics;
  private final List<HistoricalReferenceRequestDto> historicalReferences;
  private final List<DescriptiveCharacteristicReferenceRequestDto> descriptiveCharacteristics;

  private final Boolean showInMainPage;
  private final String signature;

  @Override
  public String toString() {
    return "MonumentRequestDto{" +
        "name='" + name + '\'' +
        ", regionId=" + regionId +
        ", settlementId=" + settlementId +
        ", monumentType='" + monumentType + '\'' +
        ", specialName='" + specialName + '\'' +
        ", anotherNames=" + anotherNames +
        ", history='" + history + '\'' +
        ", originalAffiliation='" + originalAffiliation + '\'' +
        ", storageUnitName='" + storageUnitName + '\'' +
        ", condition='" + condition + '\'' +
        ", pictures=" + pictures +
        ", bibliography=" + bibliography +
        ", topographics=" + topographics +
        ", historicalReferences=" + historicalReferences +
        ", descriptiveCharacteristics=" + descriptiveCharacteristics +
        ", showInMainPage=" + showInMainPage +
        ", signature=" + signature +
        '}';
  }
}
