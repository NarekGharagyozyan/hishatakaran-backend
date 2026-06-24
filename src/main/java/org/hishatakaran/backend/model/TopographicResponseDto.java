package org.hishatakaran.backend.model;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TopographicResponseDto {

    private final UUID id;
    private final String provinceArmenian;
    private final String provinceEnglish;
    private final String provinceFrench;
    private final String addressArmenian;
    private final String addressEnglish;
    private final String addressFrench;
    private final String topographyArmenian;
    private final String topographyEnglish;
    private final String topographyFrench;
    private final String distanceFromResidenceArmenian;
    private final String distanceFromResidenceEnglish;
    private final String distanceFromResidenceFrench;
    private final Integer altitude;
    private final String hydrographyArmenian;
    private final String hydrographyEnglish;
    private final String hydrographyFrench;
    private final String descriptionArmenian;
    private final String descriptionEnglish;
    private final String descriptionFrench;
}