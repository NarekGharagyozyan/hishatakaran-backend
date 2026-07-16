package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.model.TeamMemberTranslationDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMemberTranslationService {


    private final GeminiService geminiService;


    public void translate(
        TeamMembers member,
        TranslationLanguage language
    ) {
      TeamMemberTranslationDto dto;
      try {
        dto = geminiService.translateTeamMember(member, language);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Cannot translate team member", e);
      }

      applyTranslation(member, dto, language);
    }

    private void applyTranslation(
        TeamMembers member,
        TeamMemberTranslationDto dto,
        TranslationLanguage language
    ) {

        if(dto == null)
            return;

        if(language == TranslationLanguage.en) {
            member.setFullNameEn(dto.getFullName());
            member.setPositionEn(dto.getPosition());
            member.setDescriptionEn(dto.getDescription());
        } else {
            member.setFullNameFr(dto.getFullName());
            member.setPositionFr(dto.getPosition());
            member.setDescriptionFr(dto.getDescription());
        }
    }
}