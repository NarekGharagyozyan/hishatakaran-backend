package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** All three languages, saved as sent, as for the main page. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageContentEditDto {
  private LanguagesResponseDto title;
  private LanguagesResponseDto description;
  private String background;
}
