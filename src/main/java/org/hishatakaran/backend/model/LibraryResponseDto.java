package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class LibraryResponseDto {

    private final Long id;
    private final String title;
    private final String bookUrl;
    private final String coverUrl;
}