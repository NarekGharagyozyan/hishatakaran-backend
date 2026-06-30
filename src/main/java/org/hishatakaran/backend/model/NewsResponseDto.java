package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class NewsResponseDto {

    private final Long id;
    private final String titleHy;
    private final String titleEn;
    private final String titleFr;
    private final String textHy;
    private final String textEn;
    private final String textFr;
    private final List<String> pictures;
    private final String status;
}