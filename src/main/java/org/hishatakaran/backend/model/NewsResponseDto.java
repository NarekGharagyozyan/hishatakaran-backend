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
    private final LanguagesResponseDto title;
    private final LanguagesResponseDto text;
    private final List<String> images;
    private final Boolean isPublished;
}