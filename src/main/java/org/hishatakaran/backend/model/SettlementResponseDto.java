package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SettlementResponseDto {

    private final Long id;
    private final String nameHy;
    private final String nameEn;
    private final String nameFr;
}