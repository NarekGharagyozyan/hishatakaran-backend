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
public class SettlementEditDto {

  private LanguagesResponseDto name;
  private LanguagesResponseDto description;
  private String longitude;
  private String latitude;
  private List<ImageResponseDto> images;
  private Long regionId;

}
