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
        .sorted(Comparator.comparing(ExhibitionResponseDto::getDate).reversed())
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
    Exhibition exhibition = exhibitionRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Exhibition not found")
    );
    exhibition.setIsPublished(!exhibition.getIsPublished());
    Exhibition updatedExhibition = exhibitionRepository.save(exhibition);
    return ExhibitionMapper.toDto(updatedExhibition);
  }

  public ExhibitionResponseDto editExhibition(Long id, ExhibitionEditDto exhibitionEditDto) {

    Exhibition exhibition = exhibitionRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Exhibition not found")
    );

    if (exhibitionEditDto.getTitle() != null)
    {
      exhibition.setTitleHy(exhibitionEditDto.getTitle().getHy());
      exhibition.setTitleEn(exhibitionEditDto.getTitle().getEn());
      exhibition.setTitleFr(exhibitionEditDto.getTitle().getFr());
    }
    else {
      exhibition.setTitleHy(null);
      exhibition.setTitleEn(null);
      exhibition.setTitleFr(null);
    }

    if (exhibitionEditDto.getDescription() != null)
    {
      exhibition.setDescriptionHy(exhibitionEditDto.getDescription().getHy());
      exhibition.setDescriptionEn(exhibitionEditDto.getDescription().getEn());
      exhibition.setDescriptionFr(exhibitionEditDto.getDescription().getFr());
    }
    else {
      exhibition.setDescriptionHy(null);
      exhibition.setDescriptionEn(null);
      exhibition.setDescriptionFr(null);
    }

    if (exhibitionEditDto.getProgram() != null)
    {
      exhibition.setProgramHy(exhibitionEditDto.getProgram().getHy());
      exhibition.setProgramEn(exhibitionEditDto.getProgram().getEn());
      exhibition.setProgramFr(exhibitionEditDto.getProgram().getFr());
    }
    else {
      exhibition.setProgramHy(null);
      exhibition.setProgramEn(null);
      exhibition.setProgramFr(null);
    }

    if (exhibitionEditDto.getLinks() != null)
    {
      exhibition.getLinks().clear();
      exhibitionEditDto.getLinks()
          .stream()
          .map(linkResponseDto -> new ExhibitionLink(
              exhibition,
              linkResponseDto.getTitle().getHy(),
              linkResponseDto.getTitle().getEn(),
              linkResponseDto.getTitle().getFr(),
              linkResponseDto.getUrl(),
              linkResponseDto.getPdf()
          ))
          .forEach(exhibition.getLinks()::add);
    }
    else
      exhibition.setLinks(null);

    exhibition.setCover(exhibitionEditDto.getCover());
    exhibition.setDate(exhibitionEditDto.getDate());
    exhibition.setPdf(exhibitionEditDto.getPdf());

    exhibition.getVideos().clear();
    exhibitionEditDto.getVideos()
        .stream()
        .filter(dto -> dto.getTitle() != null)
        .map(dto -> new ExhibitionVideo(
            exhibition,
            dto.getTitle().getHy(),
            dto.getTitle().getEn(),
            dto.getTitle().getFr(),
            dto.getUrl()
        ))
        .forEach(exhibition.getVideos()::add);

    exhibition.getImages().clear();
    exhibitionEditDto.getImages()
        .stream()
        .map(dto -> new ExhibitionImage(
            dto.getUrl(),
            dto.getCaption() != null ? dto.getCaption().getHy() : null,
            dto.getCaption() != null ? dto.getCaption().getEn() : null,
            dto.getCaption() != null ? dto.getCaption().getFr() : null,
            exhibition
        ))
        .forEach(exhibition.getImages()::add);

    Exhibition editedExhibition = exhibitionRepository.save(exhibition);
    return ExhibitionMapper.toDto(editedExhibition);
  }

  public ExhibitionResponseDto postExhibition(
      ExhibitionRequestDto exhibitionRequestDto
  )
  {

    Exhibition exhibition = new Exhibition();

    exhibition.setIsPublished(Boolean.FALSE);
    exhibition.setDate(exhibitionRequestDto.getDate());
    exhibition.setTitleHy(exhibitionRequestDto.getTitle());
    exhibition.setDescriptionHy(exhibitionRequestDto.getDescription());
    exhibition.setProgramHy(exhibitionRequestDto.getProgram());
    if (exhibitionRequestDto.getLinks() != null) {
      exhibition.setLinks(exhibitionRequestDto.getLinks().stream()
          .map(link -> new ExhibitionLink(
              exhibition,
              link.getTitle(),
              null,
              null,
              link.getUrl(),
              link.getPdf())
          )
          .toList());
    }

    if (exhibitionRequestDto.getVideos() != null)
    {
      exhibition.setVideos(
          exhibitionRequestDto.getVideos()
              .stream()
              .map(video -> new ExhibitionVideo(
                  exhibition,
                  video.getTitle(),
                  null,
                  null,
                  video.getUrl()
              ))
              .toList()
      );
    }

    if (exhibitionRequestDto.getImages() != null)
    {
      exhibition.setImages(
          exhibitionRequestDto.getImages()
              .stream()
              .map(image -> new ExhibitionImage(
                  image.getUrl(),
                  image.getCaption(),
                  null,
                  null,
                  exhibition
              ))
              .toList()
      );
    }
    exhibition.setCover(exhibitionRequestDto.getCover());
    exhibition.setPdf(exhibitionRequestDto.getPdf());

    Exhibition savedExhibition = exhibitionRepository.save(exhibition);
    return ExhibitionMapper.toDto(savedExhibition);
  }

  @Transactional
  public void deleteExhibition(Long id) {
    Exhibition exhibition = exhibitionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Exhibition not found"));

    deleteFiles(
        exhibition.getImages()
            .stream()
            .map(ExhibitionImage::getUrl)
            .toList()
    );
    fileStorageService.deleteImage(exhibition.getCover());
    fileStorageService.deleteFile(exhibition.getPdf());

    exhibitionRepository.delete(exhibition);
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
