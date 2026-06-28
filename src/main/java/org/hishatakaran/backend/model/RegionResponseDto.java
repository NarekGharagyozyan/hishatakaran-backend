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

    private final Long id;
    private final String nameArmenian;
    private final String nameEnglish;
    private final String nameFrench;
}