package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettlementTopographicResponseDto {

  private Long id;
  private LanguagesResponseDto topography;
  private LanguagesResponseDto address;
  private String latitude;
  private String longitude;
  private LanguagesResponseDto altitude;
  private LanguagesResponseDto hydrography;
  private LanguagesResponseDto dataUpdate;
}
