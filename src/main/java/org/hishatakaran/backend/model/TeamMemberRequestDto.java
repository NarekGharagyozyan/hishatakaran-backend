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
  private String fullName;
  private String description;
  private String position;
  private String image;
  private String signature;
  private String url;

  @Override
  public String toString() {
    return "TeamMemberRequestDto{" +
        "name='" + fullName + '\'' +
        ", description='" + description + '\'' +
        ", position='" + position + '\'' +
        ", signature='" + signature + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
