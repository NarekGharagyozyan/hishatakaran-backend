package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopographicTranslationDto {

    private String region;

    private String address;

    private String topography;

    private String distanceFromResidence;

    private String hydrography;

    private String description;

    private String altitude;

}