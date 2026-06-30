package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProgramLinkRequestDto {
  private String title;
  private String url;

  @Override
  public String toString() {
    return "ProgramLinkRequestDto{" +
        "title='" + title + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
