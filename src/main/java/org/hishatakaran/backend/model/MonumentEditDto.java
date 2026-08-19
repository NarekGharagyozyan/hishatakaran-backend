package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonumentEditDto {

  private LanguagesResponseDto name;
  private LanguagesResponseDto specialName;
  private LanguagesResponseDto anotherNames;
  private Long monumentTypeId;
  private Integer monumentStatusId;
  private Long regionId;
  private Long settlementId;
  private LanguagesResponseDto history;
  private LanguagesResponseDto originalAffiliation;
  private LanguagesResponseDto storageUnitName;
  private LanguagesResponseDto individuallyCertifiablePartsOfTheStorageUnit;
  private List<ImageResponseDto> images;
  private List<MeasurementResponseDto> measurements;
  private List<MonumentVideoResponseDto> videos;
  private List<FootnoteResponseDto> footnotes;
  private List<BibliographyResponseDto> bibliography;
  private TopographicResponseDto topographics;
  private HistoricalReferenceResponseDto historicalReferences;
  private DescriptiveCharacteristicResponseDto descriptiveCharacteristics;
  private LanguagesResponseDto signature;
  private Boolean showInMainPage;

}