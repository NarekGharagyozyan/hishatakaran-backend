package org.hishatakaran.backend.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.hishatakaran.backend.entity.MonumentImage;
import org.hishatakaran.backend.entity.MonumentVideo;
import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.entity.ProgramEpisode;
import org.hishatakaran.backend.entity.ProgramImage;
import org.hishatakaran.backend.entity.ProgramLink;
import org.hishatakaran.backend.mapper.ProgramMapper;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.ProgramEditDto;
import org.hishatakaran.backend.model.ProgramRequestDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.ProgramRepository;
import org.hishatakaran.backend.repository.ProgramTypeRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramService {

  private final ProgramRepository programRepository;
  private final ProgramTypeRepository programTypeRepository;
  private final FileStorageService fileStorageService;
  private final ProgramTranslationService programTranslationService;

  public List<ProgramResponseDto> getAllPrograms() {
    return programRepository.findAll()
        .stream()
        .map(ProgramMapper::toDto)
        .sorted(Comparator.comparing(ProgramResponseDto::getId).reversed())
        .toList();
  }

  public ProgramResponseDto getProgramById(Long id) {
    return ProgramMapper.toDto(Objects.requireNonNull(programRepository.findById(id)
        .orElse(null)
    ));
  }

  @Transactional
  public ProgramResponseDto publish(
      Long id
  ) {
    Program program = programRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Program not found")
    );
    program.setIsPublished(!program.getIsPublished());
    Program updatedProgram = programRepository.save(program);
    return ProgramMapper.toDto(updatedProgram);
  }

  public ProgramResponseDto editProgram(Long id, ProgramEditDto programEditDto) {

    Program program = programRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Program not found")
    );

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

    if (programEditDto.getProgram() != null)
    {
      program.setProgramHy(programEditDto.getProgram().getHy());
      program.setProgramEn(programEditDto.getProgram().getEn());
      program.setProgramFr(programEditDto.getProgram().getFr());
    }
    else {
      program.setProgramHy(null);
      program.setProgramEn(null);
      program.setProgramFr(null);
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
    program.setProgramTypes(programTypeRepository.findById(programEditDto.getProgramTypeId()).orElseThrow(() -> new RuntimeException("Program type not found")));

    program.getEpisodes().clear();
    programEditDto.getEpisodes()
        .stream()
        .filter(dto -> dto.getTitle() != null)
        .map(dto -> new ProgramEpisode(
            program,
            dto.getTitle().getHy(),
            dto.getTitle().getEn(),
            dto.getTitle().getFr(),
            dto.getUrl()
        ))
        .forEach(program.getEpisodes()::add);

    program.getImages().clear();
    programEditDto.getImages()
        .stream()
        .map(dto -> new ProgramImage(
            dto.getUrl(),
            dto.getCaption() != null ? dto.getCaption().getHy() : null,
            dto.getCaption() != null ? dto.getCaption().getEn() : null,
            dto.getCaption() != null ? dto.getCaption().getFr() : null,
            program
        ))
        .forEach(program.getImages()::add);

    Program editedProgram = programRepository.save(program);
    return ProgramMapper.toDto(editedProgram);
  }

  public ProgramResponseDto postProgram(
      ProgramRequestDto programRequestDto
  )
  {

    Program program = new Program();

    program.setIsPublished(Boolean.FALSE);
    program.setProgramTypes(programTypeRepository.findById(program.getId()).orElseThrow(() -> new RuntimeException("Program type not found")));
    program.setTitleHy(programRequestDto.getTitle());
    program.setDescriptionHy(programRequestDto.getDescription());
    program.setProgramHy(programRequestDto.getProgram());
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

    if (programRequestDto.getEpisodes() != null)
    {
      program.setEpisodes(
          programRequestDto.getEpisodes()
              .stream()
              .map(video -> new ProgramEpisode(
                  program,
                  video.getTitle(),
                  null,
                  null,
                  video.getUrl()
              ))
              .toList()
      );
    }

    if (programRequestDto.getImages() != null)
    {
      program.setImages(
          programRequestDto.getImages()
              .stream()
              .map(image -> new ProgramImage(
                  image.getUrl(),
                  image.getCaption(),
                  null,
                  null,
                  program
              ))
              .toList()
      );
    }
    program.setCover(programRequestDto.getCover());
    program.setPdf(programRequestDto.getPdf());

    Program savedProgram = programRepository.save(program);
    return ProgramMapper.toDto(savedProgram);
  }

  @Transactional
  public void deleteProgram(Long id) {
    Program program = programRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Program not found"));

    deleteFiles(
        program.getImages()
            .stream()
            .map(ProgramImage::getUrl)
            .toList()
    );
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

    return ProgramMapper.toDto(
        programRepository.save(program)
    );
  }
}
