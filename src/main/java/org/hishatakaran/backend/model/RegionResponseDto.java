package org.hishatakaran.backend.model;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class RegionResponseDto {

    private final Integer id;
    private final String name;
    private final List<Integer> settlementIds;
    private final List<UUID> monumentIds;
}