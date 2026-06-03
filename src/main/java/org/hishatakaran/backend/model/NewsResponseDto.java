package org.hishatakaran.backend.model;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class NewsResponseDto {

    private final UUID id;
    private final String titleArmenian;
    private final String titleEnglish;
    private final String titleFrench;
    private final String textArmenian;
    private final String textEnglish;
    private final String textFrench;
    private final List<String> pictures;
    private final String status;
}