package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryRequestDto {

  private String title;
  private String description;
  private String copyrightText;
  private String copyrightUrl;
  private String authors;

  @Override
  public String toString() {
    return "LibraryRequestDto{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", copyrightText='" + copyrightText + '\'' +
        ", copyrightUrl='" + copyrightUrl + '\'' +
        ", authors='" + authors + '\'' +
        '}';
  }
}
