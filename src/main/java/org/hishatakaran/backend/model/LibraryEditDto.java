package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEditDto {

  private LanguagesResponseDto title;
  private LanguagesResponseDto description;
  private LanguagesResponseDto copyrightText;
  private String copyrightUrl;
  private String pdf;
  private String cover;
  private LanguagesResponseDto authors;


}
