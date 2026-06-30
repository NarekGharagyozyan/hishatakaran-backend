package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TopographicResponseDto {

    private final Long id;
    private final String provinceHy;
    private final String provinceEn;
    private final String provinceFr;
    private final String addressHy;
    private final String addressEn;
    private final String addressFr;
    private final String topographyHy;
    private final String topographyEn;
    private final String topographyFr;
    private final String distanceFromResidenceHy;
    private final String distanceFromResidenceEn;
    private final String distanceFromResidenceFr;
    private final String latitude;
    private final String longitude;
    private final Integer altitude;
    private final String hydrographyHy;
    private final String hydrographyEn;
    private final String hydrographyFr;
    private final String descriptionHy;
    private final String descriptionEn;
    private final String descriptionFr;
}