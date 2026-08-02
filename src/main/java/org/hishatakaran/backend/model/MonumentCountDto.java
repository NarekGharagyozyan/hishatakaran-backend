package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MonumentCountDto {

  private final Long totalCount;
  private final List<MonumentTypeCountDto> countByType;
}
