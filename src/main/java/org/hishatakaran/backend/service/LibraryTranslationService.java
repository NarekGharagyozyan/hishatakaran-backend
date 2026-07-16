package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibraryTranslationService {

    private final GeminiService geminiService;

    public void translate(
        Library library,
        TranslationLanguage language
    ) {

        try {
            geminiService.translateLibrary(library, language);
        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                "Cannot translate library",
                e
            );
        }
    }
}