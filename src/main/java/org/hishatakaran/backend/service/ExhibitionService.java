package org.hishatakaran.backend.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.hishatakaran.backend.entity.Exhibition;
import org.hishatakaran.backend.entity.ExhibitionImage;
import org.hishatakaran.backend.entity.ExhibitionLink;
import org.hishatakaran.backend.entity.ExhibitionVideo;
import org.hishatakaran.backend.mapper.ExhibitionMapper;
import org.hishatakaran.backend.model.ExhibitionEditDto;
import org.hishatakaran.backend.model.ExhibitionRequestDto;
import org.hishatakaran.backend.model.ExhibitionResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.ExhibitionRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

  private final ExhibitionRepository exhibitionRepository;
  private final FileStorageService fileStorageService;
  private final ExhibitionTranslationService exhibitionTranslationService;

  public List<ExhibitionResponseDto> getAllExhibitions() {
    return exhibitionRepository.findAll()
        .stream()
        .map(ExhibitionMapper::toDto)
        .sorted(Comparator.comparing(ExhibitionResponseDto::getId).reversed())
        .toList();
  }

  public ExhibitionResponseDto getExhibitionById(Long id) {
    return ExhibitionMapper.toDto(Objects.requireNonNull(exhibitionRepository.findById(id)
        .orElse(null)
    ));
  }

  @Transactional
  public ExhibitionResponseDto publish(
      Long id
  ) {
    Exhibition program = exhibitionRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Exhibition not found")
    );
    program.setIsPublished(!program.getIsPublished());
    Exhibition updatedExhibition = exhibitionRepository.save(program);
    return ExhibitionMapper.toDto(updatedExhibition);
  }

  public ExhibitionResponseDto editExhibition(Long id, ExhibitionEditDto programEditDto) {

    Exhibition program = exhibitionRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Exhibition not found")
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
          .map(programLinkResponseDto -> new ExhibitionLink(
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

    program.getVideos().clear();
    programEditDto.getVideos()
        .stream()
        .filter(dto -> dto.getTitle() != null)
        .map(dto -> new ExhibitionVideo(
            program,
            dto.getTitle().getHy(),
            dto.getTitle().getEn(),
            dto.getTitle().getFr(),
            dto.getUrl()
        ))
        .forEach(program.getVideos()::add);

    program.getImages().clear();
    programEditDto.getImages()
        .stream()
        .map(dto -> new ExhibitionImage(
            dto.getUrl(),
            dto.getCaption() != null ? dto.getCaption().getHy() : null,
            dto.getCaption() != null ? dto.getCaption().getEn() : null,
            dto.getCaption() != null ? dto.getCaption().getFr() : null,
            program
        ))
        .forEach(program.getImages()::add);

    Exhibition editedExhibition = exhibitionRepository.save(program);
    return ExhibitionMapper.toDto(editedExhibition);
  }

  public ExhibitionResponseDto postExhibition(
      ExhibitionRequestDto programRequestDto
  )
  {

    Exhibition program = new Exhibition();

    program.setIsPublished(Boolean.FALSE);
    program.setTitleHy(programRequestDto.getTitle());
    program.setDescriptionHy(programRequestDto.getDescription());
    program.setProgramHy(programRequestDto.getProgram());
    if (programRequestDto.getLinks() != null) {
      program.setLinks(programRequestDto.getLinks().stream()
          .map(link -> new ExhibitionLink(
              program,
              link.getTitle(),
              null,
              null,
              link.getUrl())
          )
          .toList());
    }

    if (programRequestDto.getVideos() != null)
    {
      program.setVideos(
          programRequestDto.getVideos()
              .stream()
              .map(video -> new ExhibitionVideo(
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
              .map(image -> new ExhibitionImage(
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

    Exhibition savedExhibition = exhibitionRepository.save(program);
    return ExhibitionMapper.toDto(savedExhibition);
  }

  @Transactional
  public void deleteExhibition(Long id) {
    Exhibition program = exhibitionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Exhibition not found"));

    deleteFiles(
        program.getImages()
            .stream()
            .map(ExhibitionImage::getUrl)
            .toList()
    );
    fileStorageService.deleteImage(program.getCover());
    fileStorageService.deleteFile(program.getPdf());

    exhibitionRepository.delete(program);
  }

  private void deleteFiles(List<String> paths) {
    if (paths == null) {
      return;
    }

    paths.forEach(fileStorageService::deleteImage);
  }

  @Transactional
  public ExhibitionResponseDto translate(
      Long id,
      TranslationLanguage language
  ) {

    Exhibition exhibition =
        exhibitionRepository.findById(id)
            .orElseThrow();

    exhibitionTranslationService.translate(
        exhibition,
        language
    );

    return ExhibitionMapper.toDto(
        exhibitionRepository.save(exhibition)
    );
  }
}
