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
    private final String province;
    private final String address;
    private final String topography;
    private final String distanceFromResidence;
    private final Integer altitude;
    private final String hydrography;
    private final String description;
}