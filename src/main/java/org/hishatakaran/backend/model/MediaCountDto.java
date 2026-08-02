package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MediaCountDto {

  private final Long totalCount;
  private final Long imagesCount;
  private final Long measurementsCount;
}
