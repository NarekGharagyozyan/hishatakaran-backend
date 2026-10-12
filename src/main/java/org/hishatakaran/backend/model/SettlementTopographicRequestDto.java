package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettlementTopographicRequestDto {

  private String topography;
  private String address;
  private String latitude;
  private String longitude;
  private String altitude;
  private String hydrography;
  private String dataUpdate;
}
