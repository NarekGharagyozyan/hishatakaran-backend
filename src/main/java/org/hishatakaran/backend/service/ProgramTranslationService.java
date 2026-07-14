package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramTranslationService {

    private final GeminiService geminiService;

    public void translate(
            Program program,
            TranslationLanguage language
    ) {

      try {
        geminiService.translateProgram(
                program,
                language
        );
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
}