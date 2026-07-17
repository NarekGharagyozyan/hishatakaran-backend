package org.hishatakaran.backend.model;

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
  private Long regionId;

}
