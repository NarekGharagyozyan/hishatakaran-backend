package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TeamMemberResponseDto {

  private final Long id;
  private final LanguagesResponseDto fullName;
  private final LanguagesResponseDto description;
  private final LanguagesResponseDto position;
  private final String url;
  private final String signature;
  private final String image;
}
