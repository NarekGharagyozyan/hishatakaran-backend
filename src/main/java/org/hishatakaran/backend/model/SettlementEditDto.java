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
public class SettlementEditDto {

  private Long regionId;
  private LanguagesResponseDto name;
  private Long settlementTypeId;
  private LanguagesResponseDto anotherNames;
  private LanguagesResponseDto history;
  private LanguagesResponseDto description;
  private LanguagesResponseDto dataUpdate;

  private SettlementTopographicResponseDto topographics;
  private List<BibliographyResponseDto> bibliography;
  private List<ImageResponseDto> images;
  private List<MeasurementResponseDto> measurements;
  private List<MonumentVideoResponseDto> videos;
}
