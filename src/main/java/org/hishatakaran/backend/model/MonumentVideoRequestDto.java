package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonumentVideoRequestDto {

  private String title;
  private String url;


  @Override
  public String toString() {
    return "MonumentVideoRequestDto{" +
        "title='" + title + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
