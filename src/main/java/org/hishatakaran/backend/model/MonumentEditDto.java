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
  private LanguagesResponseDto monumentType;
  private Long regionId;
  private Long settlementId;
  private LanguagesResponseDto history;
  private LanguagesResponseDto originalAffiliation;
  private LanguagesResponseDto storageUnitName;
  private LanguagesResponseDto condition;
  private List<String> images;
  private List<String> measurements;
  private List<MonumentVideoResponseDto> videos;
  private Boolean isPublished;
  private List<BibliographyResponseDto> bibliography;
  private TopographicResponseDto topographics;
  private HistoricalReferenceResponseDto historicalReferences;
  private DescriptiveCharacteristicResponseDto descriptiveCharacteristics;
  private String signature;
  private Boolean showInMainPage;

}