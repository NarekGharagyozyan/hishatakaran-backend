package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.AboutUs;
import org.hishatakaran.backend.mapper.AboutUsMapper;
import org.hishatakaran.backend.model.AboutUsEditDto;
import org.hishatakaran.backend.model.AboutUsRequestDto;
import org.hishatakaran.backend.model.AboutUsResponseDto;
import org.hishatakaran.backend.model.AboutUsTranslationDto;
import org.hishatakaran.backend.repository.AboutUsRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AboutUsService {

  private final AboutUsRepository aboutUsRepository;
  private final GeminiService geminiService;

  public AboutUsResponseDto createAboutUs(AboutUsRequestDto aboutUsRequestDto) {
    List<AboutUs> all = aboutUsRepository.findAll();
    if (all.size() > 1) {
      throw new RuntimeException("Something went wrong");
    }

    AboutUsTranslationDto translation;

    try {
      translation = geminiService.translateAboutUs(aboutUsRequestDto);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    AboutUs aboutUs;

    if (all.isEmpty()) {
      aboutUs = new AboutUs();
    }
    else {
      aboutUs = all.getFirst();
    }

    aboutUs.setTitleHy(translation.getTitleHy());
    aboutUs.setTitleEn(translation.getTitleEn());
    aboutUs.setTitleFr(translation.getTitleFr());
    aboutUs.setSubtitleHy(translation.getSubtitleHy());
    aboutUs.setSubtitleEn(translation.getSubtitleEn());
    aboutUs.setSubtitleFr(translation.getSubtitleFr());
    aboutUs.setTextHy(translation.getTextHy());
    aboutUs.setTextEn(translation.getTextEn());
    aboutUs.setTextFr(translation.getTextFr());

    aboutUs.setBackground(aboutUsRequestDto.getBackground());

    return AboutUsMapper.toResponseDto(aboutUsRepository.save(aboutUs));
  }

  public AboutUsResponseDto getAboutUs() {
    return AboutUsMapper.toResponseDto(aboutUsRepository.findAll().getFirst());
  }

  public AboutUsResponseDto editAboutUs(AboutUsEditDto aboutUsEditDto)
  {
    AboutUs aboutUs = aboutUsRepository.findAll().getFirst();
    aboutUs.setTitleHy(aboutUsEditDto.getTitle().getHy());
    aboutUs.setTitleEn(aboutUsEditDto.getTitle().getEn());
    aboutUs.setTitleFr(aboutUsEditDto.getTitle().getFr());
    aboutUs.setSubtitleHy(aboutUsEditDto.getSubtitle().getHy());
    aboutUs.setSubtitleEn(aboutUsEditDto.getSubtitle().getEn());
    aboutUs.setSubtitleFr(aboutUsEditDto.getSubtitle().getFr());
    aboutUs.setTextHy(aboutUsEditDto.getText().getHy());
    aboutUs.setTextEn(aboutUsEditDto.getText().getEn());
    aboutUs.setTextFr(aboutUsEditDto.getText().getFr());
    aboutUs.setBackground(aboutUsEditDto.getBackground());

    return AboutUsMapper.toResponseDto(aboutUsRepository.save(aboutUs));
  }
}
