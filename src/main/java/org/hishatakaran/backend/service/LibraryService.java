package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.model.LibraryAiResponseDto;
import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryRepository libraryRepository;
  private final ObjectMapper objectMapper;
  private final GeminiService geminiService;

  public LibraryResponseDto postBook(
      LibraryRequestDto libraryRequestDto
  )
  {

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
        libraryRequestDto.getPdf(),
        libraryRequestDto.getCover(),
        libraryAiResponseDto.getAuthorsHy(),
        libraryAiResponseDto.getAuthorsEn(),
        libraryAiResponseDto.getAuthorsFr()
    );
    Library savedBook = libraryRepository.save(library);
    return LibraryMapper.toDto(savedBook);
  }
}
