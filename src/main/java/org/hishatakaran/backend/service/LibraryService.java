package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.model.LibraryEditDto;
import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryRepository libraryRepository;
  private final LibraryTranslationService libraryTranslationService;
  private final FileStorageService fileStorageService;

  public LibraryResponseDto postBook(
      LibraryRequestDto libraryRequestDto
  )
  {
    Library library = new Library(
        libraryRequestDto.getTitle(),
        null,
        null,
        libraryRequestDto.getDescription(),
        null,
        null,
        libraryRequestDto.getCopyrightText(),
        null,
        null,
        libraryRequestDto.getCopyrightUrl(),
        libraryRequestDto.getPdf(),
        libraryRequestDto.getCover(),
        libraryRequestDto.getAuthors(),
        null,
        null
    );
    return LibraryMapper.toDto(libraryRepository.save(library));
  }

  @Transactional
  public LibraryResponseDto translate(
      Long id,
      TranslationLanguage language
  ) {

    Library library =
        libraryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Library not found"));

    libraryTranslationService.translate(library, language);
    return LibraryMapper.toDto(libraryRepository.save(library));
  }

  public LibraryResponseDto editLibrary(
      @PathVariable Long id,
      @RequestBody LibraryEditDto libraryEditDto
  )
  {

    Library library = libraryRepository.findById(id).orElseThrow(() -> new RuntimeException("Library not found"));

    if (libraryEditDto.getTitle() != null)
    {
      library.setTitleHy(libraryEditDto.getTitle().getHy());
      library.setTitleEn(libraryEditDto.getTitle().getEn());
      library.setTitleFr(libraryEditDto.getTitle().getFr());
    }
    else {
      library.setTitleHy(null);
      library.setTitleEn(null);
      library.setTitleFr(null);
    }

    if (libraryEditDto.getDescription() != null)
    {
      library.setDescriptionHy(libraryEditDto.getDescription().getHy());
      library.setDescriptionEn(libraryEditDto.getDescription().getEn());
      library.setDescriptionFr(libraryEditDto.getDescription().getFr());
    }
    else {
      library.setDescriptionHy(null);
      library.setDescriptionEn(null);
      library.setDescriptionFr(null);
    }

    if (libraryEditDto.getCopyrightText() != null)
    {
      library.setCopyrightTextHy(libraryEditDto.getCopyrightText().getHy());
      library.setCopyrightTextEn(libraryEditDto.getCopyrightText().getEn());
      library.setCopyrightTextFr(libraryEditDto.getCopyrightText().getFr());
    }
    else {
      library.setCopyrightTextHy(null);
      library.setCopyrightTextEn(null);
      library.setCopyrightTextFr(null);
    }

    if (libraryEditDto.getAuthors() != null)
    {
      library.setAuthorsHy(libraryEditDto.getAuthors().getHy());
      library.setAuthorsEn(libraryEditDto.getAuthors().getEn());
      library.setAuthorsFr(libraryEditDto.getAuthors().getFr());
    }
    else {
      library.setAuthorsHy(null);
      library.setAuthorsEn(null);
      library.setAuthorsFr(null);
    }

    library.setCopyrightUrl(libraryEditDto.getCopyrightUrl());
    library.setBookUrl(libraryEditDto.getPdf());
    library.setCoverUrl(libraryEditDto.getCover());

    return LibraryMapper.toDto(libraryRepository.save(library));
  }

  @Transactional
  public void deleteLibrary(Long id) {
    Library library = libraryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Library not found"));

    fileStorageService.deleteImage(library.getCoverUrl());
    fileStorageService.deleteFile(library.getBookUrl());

    libraryRepository.delete(library);
  }

}
