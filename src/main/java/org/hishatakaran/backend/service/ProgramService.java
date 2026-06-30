package org.hishatakaran.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.entity.ProgramLink;
import org.hishatakaran.backend.mapper.ProgramMapper;
import org.hishatakaran.backend.model.ProgramAiResponseDto;
import org.hishatakaran.backend.model.ProgramRequestDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ProgramService {

  private final ProgramRepository programRepository;
  private final ObjectMapper objectMapper;
  private final GeminiService geminiService;
  private final FileStorageService fileStorageService;

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
    ProgramRequestDto programRequestDto = objectMapper.readValue(stringData, ProgramRequestDto.class);

    String requestGeminiForPrograms = geminiService.requestGeminiForPrograms(programRequestDto);
    ProgramAiResponseDto programAiResponseDto = objectMapper.readValue(
        requestGeminiForPrograms,
        ProgramAiResponseDto.class
    );

    Program program = new Program(
        Status.DRAFT,
        programAiResponseDto.getTitleHy(),
        programAiResponseDto.getTitleEn(),
        programAiResponseDto.getTitleFr(),
        programAiResponseDto.getDescriptionHy(),
        programAiResponseDto.getDescriptionEn(),
        programAiResponseDto.getDescriptionFr(),
        images.stream()
            .map(image -> fileStorageService.saveImage(image, "programs"))
            .toList(),
        fileStorageService.savefile(pdf, "programs"),
        fileStorageService.saveImage(cover, "programs"),
        new ArrayList<>()
    );
    program.setLinks(programAiResponseDto.getLinks().stream()
        .map(link -> new ProgramLink(
            program,
            link.getLinkTitleHy(),
            link.getLinkTitleEn(),
            link.getLinkTitleFr(),
            link.getLink())
        )
        .toList());

    Program savedProgram = programRepository.save(program);
    return ProgramMapper.toResponseDto(savedProgram);
  }

}
