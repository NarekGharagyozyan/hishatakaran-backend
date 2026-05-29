package org.hishatakaran.backend.model;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class BibliographyResponseDto {

    private final UUID id;
    private final List<String> urls;
}