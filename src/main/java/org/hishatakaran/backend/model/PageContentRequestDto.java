package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Armenian only: English and French are translated, as for the main page. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageContentRequestDto {
  private String title;
  private String description;
  private String background;
}
