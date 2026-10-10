package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SettlementTypeResponseDto {

  private final Long id;
  private final LanguagesResponseDto name;
}
