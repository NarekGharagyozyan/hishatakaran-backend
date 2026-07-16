package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberEditDto {
  private LanguagesResponseDto fullName;
  private LanguagesResponseDto description;
  private LanguagesResponseDto position;
  private String url;
  private String signature;
  private String image;
}
