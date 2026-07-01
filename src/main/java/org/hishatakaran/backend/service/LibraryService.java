package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.model.LibraryAiResponseDto;
import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryRepository libraryRepository;
  private final ObjectMapper objectMapper;
  private final GeminiService geminiService;
  private final FileStorageService fileStorageService;

  public LibraryResponseDto postBook(
      String stringData,
      MultipartFile pdf,
      MultipartFile cover
  )
  {

    LibraryRequestDto libraryRequestDto = objectMapper.readValue(stringData, LibraryRequestDto.class);
    String requestGeminiForLibrary = geminiService.requestGeminiForLibrary(libraryRequestDto);
    LibraryAiResponseDto libraryAiResponseDto = objectMapper.readValue(requestGeminiForLibrary,
        LibraryAiResponseDto.class);

    Library library = new Library(
        libraryAiResponseDto.getTitleHy(),
        libraryAiResponseDto.getTitleEn(),
        libraryAiResponseDto.getTitleFr(),
        libraryAiResponseDto.getDescriptionHy(),
        libraryAiResponseDto.getDescriptionEn(),
        libraryAiResponseDto.getDescriptionFr(),
        libraryAiResponseDto.getCopyrightTextHy(),
        libraryAiResponseDto.getCopyrightTextEn(),
        libraryAiResponseDto.getCopyrightTextFr(),
        libraryRequestDto.getCopyrightUrl(),
        fileStorageService.savefile(pdf, "library"),
        fileStorageService.saveImage(cover, "library"),
        libraryAiResponseDto.getAuthorsHy(),
        libraryAiResponseDto.getAuthorsEn(),
        libraryAiResponseDto.getAuthorsFr()
    );
    Library savedBook = libraryRepository.save(library);
    return LibraryMapper.toDto(savedBook);
  }
}
