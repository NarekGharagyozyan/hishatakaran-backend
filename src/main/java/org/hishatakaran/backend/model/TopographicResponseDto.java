package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TopographicResponseDto {

    private final Long id;
    private final LanguagesResponseDto region;
    private final LanguagesResponseDto address;
    private final LanguagesResponseDto topography;
    private final LanguagesResponseDto distanceFromResidence;
    private final String latitude;
    private final String longitude;
    private final LanguagesResponseDto altitude;
    private final LanguagesResponseDto hydrography;
    private final LanguagesResponseDto description;
}