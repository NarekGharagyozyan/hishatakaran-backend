package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.CulturalHeritages;
import org.hishatakaran.backend.mapper.CulturalHeritagesMapper;
import org.hishatakaran.backend.model.CulturalHeritagesEditDto;
import org.hishatakaran.backend.model.CulturalHeritagesRequestDto;
import org.hishatakaran.backend.model.CulturalHeritagesResponseDto;
import org.hishatakaran.backend.model.CulturalHeritagesTranslationDto;
import org.hishatakaran.backend.repository.CulturalHeritagesRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CulturalHeritagesService {

  private final CulturalHeritagesRepository culturalHeritagesRepository;
  private final GeminiService geminiService;

  public CulturalHeritagesResponseDto createCulturalHeritages(CulturalHeritagesRequestDto culturalHeritagesRequestDto) {
    List<CulturalHeritages> all = culturalHeritagesRepository.findAll();
    if (all.size() > 1) {
      throw new RuntimeException("Something went wrong");
    }

    CulturalHeritagesTranslationDto translation;

    try {
      translation = geminiService.translateCulturalHeritages(culturalHeritagesRequestDto);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    CulturalHeritages culturalHeritages;

    if (all.isEmpty()) {
      culturalHeritages = new CulturalHeritages();
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

    return CulturalHeritagesMapper.toResponseDto(culturalHeritagesRepository.save(culturalHeritages));
  }

  public CulturalHeritagesResponseDto getCulturalHeritages() {
    List<CulturalHeritages> culturalHeritages = culturalHeritagesRepository.findAll();
    if (culturalHeritages.isEmpty())
      return null;
    return CulturalHeritagesMapper.toResponseDto(culturalHeritages.getFirst());
  }

  public CulturalHeritagesResponseDto editCulturalHeritages(CulturalHeritagesEditDto culturalHeritagesEditDto)
  {
    CulturalHeritages culturalHeritages = culturalHeritagesRepository.findAll().getFirst();
    culturalHeritages.setTitleHy(culturalHeritagesEditDto.getTitle().getHy());
    culturalHeritages.setTitleEn(culturalHeritagesEditDto.getTitle().getEn());
    culturalHeritages.setTitleFr(culturalHeritagesEditDto.getTitle().getFr());
    culturalHeritages.setSubtitleHy(culturalHeritagesEditDto.getSubtitle().getHy());
    culturalHeritages.setSubtitleEn(culturalHeritagesEditDto.getSubtitle().getEn());
    culturalHeritages.setSubtitleFr(culturalHeritagesEditDto.getSubtitle().getFr());
    culturalHeritages.setBackground(culturalHeritagesEditDto.getBackground());

    return CulturalHeritagesMapper.toResponseDto(culturalHeritagesRepository.save(culturalHeritages));
  }
}
