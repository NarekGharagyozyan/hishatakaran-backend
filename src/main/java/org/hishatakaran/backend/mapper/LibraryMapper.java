package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.LibraryResponseDto;

public class LibraryMapper {

  public static LibraryResponseDto toDto(Library library) {

    return new LibraryResponseDto(
        library.getId(),
        library.getTitleHy() != null
            ? LanguagesResponseDto.of(
                library.getTitleHy(),
                library.getTitleEn(),
                library.getTitleFr()
            )
            : null,
        library.getDescriptionHy() != null && !library.getDescriptionHy().isBlank() && !library.getDescriptionHy().equals("null")
            ? LanguagesResponseDto.of(
                library.getDescriptionHy(),
                library.getDescriptionEn(),
                library.getDescriptionFr()
            )
          : null,
        library.getCopyrightTextHy() != null && !library.getCopyrightTextHy().isBlank() && !library.getCopyrightTextHy().equals("null")
            ? LanguagesResponseDto.of(
              library.getCopyrightTextHy(),
              library.getCopyrightTextEn(),
              library.getCopyrightTextFr()
          )
          : null,
        library.getCopyrightUrl(),
        library.getBookUrl(),
        library.getCoverUrl(),
        library.getAuthorsHy() != null && !library.getAuthorsHy().isBlank() && !library.getAuthorsHy().equals("null")
            ? LanguagesResponseDto.of(
              library.getAuthorsHy(),
              library.getAuthorsEn(),
              library.getAuthorsFr()
          )
          : null
    );
  }
}
