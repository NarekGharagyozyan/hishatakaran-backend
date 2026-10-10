package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettlementTopographicTranslationDto {

    private String topography;

    private String address;

    private String altitude;

    private String hydrography;

    private String dataUpdate;
}
