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
    private final String title;
    private final String text;
    private final List<String> pictures;
    private final String status;
}