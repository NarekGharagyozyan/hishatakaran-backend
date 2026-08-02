package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SettlementsCountDto {

  private final Long totalCount;
  private final List<SettlementCountDto> countBySettlement;
}
