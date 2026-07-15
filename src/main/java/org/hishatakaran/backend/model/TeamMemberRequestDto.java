package org.hishatakaran.backend.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberRequestDto {
  private String name;
  private String surname;
  private String description;
  private String position;
  private String url;

  @Override
  public String toString() {
    return "TeamMemberRequestDto{" +
        "name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", description='" + description + '\'' +
        ", position='" + position + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
