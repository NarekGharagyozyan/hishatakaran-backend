package org.hishatakaran.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementRequestDto {

  private String name;
  private Long settlementTypeId;
  private String anotherNames;
  private String history;
  private String description;
  private String dataUpdate;

  private SettlementTopographicRequestDto topographics;
  private List<BibliographyRequestDto> bibliography;
  private List<ImageRequestDto> images;
  private List<MeasurementRequestDto> measurements;
  private List<MonumentVideoRequestDto> videos;
}
