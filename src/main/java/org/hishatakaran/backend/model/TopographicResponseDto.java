package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TopographicResponseDto {

    private final Long id;
    private final LanguagesResponseDto province;
    private final LanguagesResponseDto address;
    private final LanguagesResponseDto topography;
    private final LanguagesResponseDto distanceFromResidence;
    private final String latitude;
    private final String longitude;
    private final Integer altitude;
    private final LanguagesResponseDto hydrography;
    private final LanguagesResponseDto description;
}