package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SettlementResponseDto {

    private final Long id;
    private final LanguagesResponseDto name;
    private final LanguagesResponseDto description;
    private final String longitude;
    private final String latitude;
    private final List<ImageResponseDto> images;
    private final Long regionId;
}