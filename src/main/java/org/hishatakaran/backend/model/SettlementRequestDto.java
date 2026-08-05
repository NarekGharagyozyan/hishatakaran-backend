package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SettlementRequestDto {

  private final String name;
  private final String description;
  private final String longitude;
  private final String latitude;
  private final List<ImageRequestDto> images;

}
