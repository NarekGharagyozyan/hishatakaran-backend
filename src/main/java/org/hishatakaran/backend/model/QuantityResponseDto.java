package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class QuantityResponseDto {

  private final MonumentCountDto registered;
  private final MonumentCountDto documented;
  private final SettlementsCountDto settlements;
  private final MediaCountDto media;
}
