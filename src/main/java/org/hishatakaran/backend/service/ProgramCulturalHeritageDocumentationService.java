package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.ProgramCulturalHeritageDocumentation;
import org.hishatakaran.backend.mapper.ProgramCulturalHeritageDocumentationMapper;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationEditDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationRequestDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationResponseDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationTranslationDto;
import org.hishatakaran.backend.repository.ProgramCulturalHeritageDocumentationRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramCulturalHeritageDocumentationService {

  private final ProgramCulturalHeritageDocumentationRepository programsCulturalHeritageDocumentationRepository;
  private final GeminiService geminiService;

  public ProgramCulturalHeritageDocumentationResponseDto createProgramCulturalHeritageDocumentation(
      ProgramCulturalHeritageDocumentationRequestDto culturalHeritagesRequestDto) {
    List<ProgramCulturalHeritageDocumentation> all = programsCulturalHeritageDocumentationRepository.findAll();
    if (all.size() > 1) {
      throw new RuntimeException("Something went wrong");
    }

    ProgramCulturalHeritageDocumentationTranslationDto translation;

    try {
      translation = geminiService.translateProgramCulturalHeritageDocumentation(culturalHeritagesRequestDto);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    ProgramCulturalHeritageDocumentation culturalHeritages;

    if (all.isEmpty()) {
      culturalHeritages = new ProgramCulturalHeritageDocumentation();
    }
    else {
      culturalHeritages = all.getFirst();
    }

    culturalHeritages.setTitleHy(translation.getTitleHy());
    culturalHeritages.setTitleEn(translation.getTitleEn());
    culturalHeritages.setTitleFr(translation.getTitleFr());
    culturalHeritages.setSubtitleHy(translation.getSubtitleHy());
    culturalHeritages.setSubtitleEn(translation.getSubtitleEn());
    culturalHeritages.setSubtitleFr(translation.getSubtitleFr());

    culturalHeritages.setBackground(culturalHeritagesRequestDto.getBackground());

    return ProgramCulturalHeritageDocumentationMapper.toResponseDto(programsCulturalHeritageDocumentationRepository.save(culturalHeritages));
  }

  public ProgramCulturalHeritageDocumentationResponseDto getProgramCulturalHeritageDocumentation() {
    List<ProgramCulturalHeritageDocumentation> culturalHeritages = programsCulturalHeritageDocumentationRepository.findAll();
    if (culturalHeritages.isEmpty())
      return null;
    return ProgramCulturalHeritageDocumentationMapper.toResponseDto(culturalHeritages.getFirst());
  }

  public ProgramCulturalHeritageDocumentationResponseDto editProgramCulturalHeritageDocumentation(ProgramCulturalHeritageDocumentationEditDto culturalHeritagesEditDto)
  {

    ProgramCulturalHeritageDocumentation culturalHeritages = programsCulturalHeritageDocumentationRepository.findAll().getFirst();
    culturalHeritages.setTitleHy(culturalHeritagesEditDto.getTitle().getHy());
    culturalHeritages.setTitleEn(culturalHeritagesEditDto.getTitle().getEn());
    culturalHeritages.setTitleFr(culturalHeritagesEditDto.getTitle().getFr());
    culturalHeritages.setSubtitleHy(culturalHeritagesEditDto.getSubtitle().getHy());
    culturalHeritages.setSubtitleEn(culturalHeritagesEditDto.getSubtitle().getEn());
    culturalHeritages.setSubtitleFr(culturalHeritagesEditDto.getSubtitle().getFr());
    culturalHeritages.setBackground(culturalHeritagesEditDto.getBackground());

    return ProgramCulturalHeritageDocumentationMapper.toResponseDto(programsCulturalHeritageDocumentationRepository.save(culturalHeritages));
  }
}
