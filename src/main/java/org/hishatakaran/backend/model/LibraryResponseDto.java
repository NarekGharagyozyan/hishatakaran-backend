package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class LibraryResponseDto {

    private final Long id;
    private final LanguagesResponseDto title;
    private final LanguagesResponseDto description;
    private final LanguagesResponseDto copyrightText;
    private final String copyrightUrl;
    private final String bookUrl;
    private final String coverUrl;
    private final LanguagesResponseDto authors;
}