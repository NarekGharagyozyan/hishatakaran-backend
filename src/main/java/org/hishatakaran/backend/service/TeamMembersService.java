package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.mapper.TeamMemberMapper;
import org.hishatakaran.backend.model.TeamMemberAiResponseDto;
import org.hishatakaran.backend.model.TeamMemberRequestDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;
import org.hishatakaran.backend.repository.TeamMembersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMembersService {

  private final TeamMembersRepository teamMembersRepository;
  private final ObjectMapper objectMapper;
  private final GeminiService geminiService;
  private final FileStorageService fileStorageService;

  @Transactional
  public TeamMemberResponseDto addNewTeamMember(String teamMemberString, MultipartFile image) {
    TeamMemberRequestDto teamMemberRequestDto;
    try {
      teamMemberRequestDto = objectMapper.readValue(teamMemberString, TeamMemberRequestDto.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    String geminiForTeamMemberString = geminiService.requestGeminiForTeamMember(teamMemberRequestDto);
    TeamMemberAiResponseDto teamMemberAiResponseDto;
    try {
      teamMemberAiResponseDto = objectMapper.readValue(geminiForTeamMemberString, TeamMemberAiResponseDto.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    TeamMembers newTeamMemberEntity = new TeamMembers(
        teamMemberAiResponseDto.getNameHy(),
        teamMemberAiResponseDto.getNameEn(),
        teamMemberAiResponseDto.getNameFr(),
        teamMemberAiResponseDto.getSurnameHy(),
        teamMemberAiResponseDto.getSurnameEn(),
        teamMemberAiResponseDto.getSurnameFr(),
        teamMemberAiResponseDto.getPositionHy(),
        teamMemberAiResponseDto.getPositionEn(),
        teamMemberAiResponseDto.getPositionFr(),
        teamMemberAiResponseDto.getDescriptionHy(),
        teamMemberAiResponseDto.getDescriptionEn(),
        teamMemberAiResponseDto.getDescriptionFr(),
        fileStorageService.saveImage(image, "members"),
        teamMemberRequestDto.getUrl()
    );
    TeamMembers newTeamMember = teamMembersRepository.save(newTeamMemberEntity);
    return TeamMemberMapper.toResponseDto(newTeamMember);
  }

  public List<TeamMemberResponseDto> getAllTeamMembers() {
    return teamMembersRepository.findAll()
        .stream()
        .map(TeamMemberMapper::toResponseDto)
        .toList();
  }
}
