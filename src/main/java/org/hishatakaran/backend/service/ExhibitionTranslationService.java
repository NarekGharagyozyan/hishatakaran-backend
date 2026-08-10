package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.Exhibition;
import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExhibitionTranslationService {

  private final GeminiService geminiService;

  public void translate(
      Exhibition exhibition,
      TranslationLanguage language
  ) {

    try {
      geminiService.translateExhibition(
          exhibition,
          language
      );
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
