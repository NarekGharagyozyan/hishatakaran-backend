package org.hishatakaran.backend.service;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.entity.ProgramLink;
import org.hishatakaran.backend.mapper.ProgramMapper;
import org.hishatakaran.backend.model.ProgramAiResponseDto;
import org.hishatakaran.backend.model.ProgramRequestDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ProgramService {

  private final ProgramRepository programRepository;
  private final ObjectMapper objectMapper;
  private final GeminiService geminiService;
  private final FileStorageService fileStorageService;
  private final ProgramTranslationService programTranslationService;

  public List<ProgramResponseDto> getAllPrograms() {
    return programRepository.findAll()
        .stream()
        .map(ProgramMapper::toResponseDto)
        .toList();
  }

  public ProgramResponseDto getProgramById(Long id) {
    return ProgramMapper.toResponseDto(Objects.requireNonNull(programRepository.findById(id).orElse(null)));
  }

  public ProgramResponseDto postProgram(
      String stringData,
      List<MultipartFile> images,
      MultipartFile pdf,
      MultipartFile cover
  )
  {
    ProgramRequestDto programRequestDto = null;
    if (stringData != null)
    {
      programRequestDto = objectMapper.readValue(stringData, ProgramRequestDto.class);
    }

    Program program = new Program();

    program.setIsPublished(Boolean.FALSE);
    if (programRequestDto != null)
    {
      program.setTitleHy(programRequestDto.getTitle());
      program.setDescriptionHy(programRequestDto.getDescription());
      if (programRequestDto.getLinks() != null) {
        program.setLinks(programRequestDto.getLinks().stream()
            .map(link -> new ProgramLink(
                program,
                link.getTitle(),
                null,
                null,
                link.getUrl())
            )
            .toList());
      }
    }
    List<String> savedImages = null;
    if (images != null)
    {
       savedImages = images.stream()
          .map(image -> fileStorageService.saveImage(image, "programs"))
          .toList();
    }
    program.setImages(savedImages);
    program.setPdf(fileStorageService.savefile(pdf, "programs"));
    program.setCover(fileStorageService.saveImage(cover, "programs"));

    Program savedProgram = programRepository.save(program);
    return ProgramMapper.toResponseDto(savedProgram);
  }

  @Transactional
  public ProgramResponseDto translate(
      Long id,
      TranslationLanguage language
  ) {

    Program program =
        programRepository.findById(id)
            .orElseThrow();

    programTranslationService.translate(
        program,
        language
    );

    return ProgramMapper.toResponseDto(
        programRepository.save(program)
    );
  }
}
