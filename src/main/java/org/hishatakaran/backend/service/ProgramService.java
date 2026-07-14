package org.hishatakaran.backend.service;

import java.util.List;
import java.util.Objects;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.entity.ProgramLink;
import org.hishatakaran.backend.mapper.ProgramMapper;
import org.hishatakaran.backend.model.ProgramEditDto;
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

  public ProgramResponseDto editProgram(Long id, ProgramEditDto programEditDto) {

    Program program = programRepository.findById(id).orElseThrow(() -> new RuntimeException("Program not found"));

    program.setIsPublished(programEditDto.getIsPublished());

    if (programEditDto.getTitle() != null)
    {
      program.setTitleHy(programEditDto.getTitle().getHy());
      program.setTitleEn(programEditDto.getTitle().getEn());
      program.setTitleFr(programEditDto.getTitle().getFr());
    }
    else {
      program.setTitleHy(null);
      program.setTitleEn(null);
      program.setTitleFr(null);
    }

    if (programEditDto.getDescription() != null)
    {
      program.setDescriptionHy(programEditDto.getDescription().getHy());
      program.setDescriptionEn(programEditDto.getDescription().getEn());
      program.setDescriptionFr(programEditDto.getDescription().getFr());
    }
    else {
      program.setDescriptionHy(null);
      program.setDescriptionEn(null);
      program.setDescriptionFr(null);
    }

    if (programEditDto.getLinks() != null)
    {
      program.getLinks().clear();
      programEditDto.getLinks()
          .stream()
          .map(programLinkResponseDto -> new ProgramLink(
              program,
              programLinkResponseDto.getTitle().getHy(),
              programLinkResponseDto.getTitle().getEn(),
              programLinkResponseDto.getTitle().getFr(),
              programLinkResponseDto.getUrl()
          ))
          .forEach(program.getLinks()::add);
    }
    else
      program.setLinks(null);

    program.setCover(programEditDto.getCover());
    program.setPdf(programEditDto.getPdf());
    program.setImages(programEditDto.getImages());

    Program editedProgram = programRepository.save(program);
    return ProgramMapper.toResponseDto(editedProgram);
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
  public void deleteProgram(Long id) {
    Program program = programRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Program not found"));

    deleteFiles(program.getImages());
    fileStorageService.deleteImage(program.getCover());
    fileStorageService.deleteFile(program.getPdf());

    programRepository.delete(program);
  }

  private void deleteFiles(List<String> paths) {
    if (paths == null) {
      return;
    }

    paths.forEach(fileStorageService::deleteImage);
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
