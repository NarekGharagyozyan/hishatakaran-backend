package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class TopographicRequestDto {

  private String region;
  private String address;
  private String topography;
  private String distanceFromResidence;
  private Integer altitude;
  private String hydrography;
  private String description;

  @Override
  public String toString() {
    return "TopographicRequestDto{" +
        "region='" + region + '\'' +
        ", address='" + address + '\'' +
        ", topography='" + topography + '\'' +
        ", distanceFromResidence='" + distanceFromResidence + '\'' +
        ", altitude=" + altitude +
        ", hydrography='" + hydrography + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
