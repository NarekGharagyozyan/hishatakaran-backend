package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.model.LibraryResponseDto;

public class LibraryMapper {

  public static LibraryResponseDto toDto(Library library) {

    return new LibraryResponseDto(
        library.getId(),
        library.getTitle(),
        library.getBookUrl(),
        library.getCoverUrl()
    );
  }
}
