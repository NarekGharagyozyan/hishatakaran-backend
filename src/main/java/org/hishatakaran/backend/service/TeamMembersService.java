package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.mapper.TeamMemberMapper;
import org.hishatakaran.backend.model.TeamMemberEditDto;
import org.hishatakaran.backend.model.TeamMemberRequestDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.TeamMembersRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMembersService {

  private final TeamMembersRepository teamMembersRepository;
  private final TeamMemberTranslationService teamMemberTranslationService;
  private final FileStorageService fileStorageService;

  @Transactional
  public TeamMemberResponseDto addNewTeamMember(TeamMemberRequestDto teamMemberRequestDto) {

    TeamMembers newTeamMemberEntity = new TeamMembers(
        teamMemberRequestDto.getFullName(),
        null,
        null,
        teamMemberRequestDto.getPosition(),
        null,
        null,
        teamMemberRequestDto.getDescription(),
        null,
        null,
        teamMemberRequestDto.getImage(),
        teamMemberRequestDto.getSignature(),
        teamMemberRequestDto.getUrl()
    );
    return TeamMemberMapper.toDto(teamMembersRepository.save(newTeamMemberEntity));
  }

  @Transactional
  public TeamMemberResponseDto translate(
      Long id,
      TranslationLanguage language
  ) {
    TeamMembers member = teamMembersRepository.findById(id).orElseThrow();
    teamMemberTranslationService.translate(member, language);
    return TeamMemberMapper.toDto(teamMembersRepository.save(member));
  }

  public TeamMemberResponseDto editTeamMemberInformation(
      Long id,
      TeamMemberEditDto teamMemberEditDto)
  {
    TeamMembers teamMember = teamMembersRepository.findById(id).orElseThrow();

    if (teamMemberEditDto.getFullName() != null)
    {
      teamMember.setFullNameHy(teamMemberEditDto.getFullName().getHy());
      teamMember.setFullNameEn(teamMemberEditDto.getFullName().getEn());
      teamMember.setFullNameFr(teamMemberEditDto.getFullName().getFr());
    }
    else
    {
      teamMember.setFullNameHy(null);
      teamMember.setFullNameEn(null);
      teamMember.setFullNameFr(null);
    }

    if (teamMemberEditDto.getPosition() != null)
    {
      teamMember.setPositionHy(teamMemberEditDto.getPosition().getHy());
      teamMember.setPositionEn(teamMemberEditDto.getPosition().getEn());
      teamMember.setPositionFr(teamMemberEditDto.getPosition().getFr());
    }
    else
    {
      teamMember.setPositionHy(null);
      teamMember.setPositionEn(null);
      teamMember.setPositionFr(null);
    }

    if (teamMemberEditDto.getDescription() != null)
    {
      teamMember.setDescriptionHy(teamMemberEditDto.getDescription().getHy());
      teamMember.setDescriptionEn(teamMemberEditDto.getDescription().getEn());
      teamMember.setDescriptionFr(teamMemberEditDto.getDescription().getFr());
    }
    else
    {
      teamMember.setDescriptionHy(null);
      teamMember.setDescriptionEn(null);
      teamMember.setDescriptionFr(null);
    }

    teamMember.setImage(teamMemberEditDto.getImage());
    teamMember.setUrl(teamMemberEditDto.getUrl());

    return TeamMemberMapper.toDto(teamMembersRepository.save(teamMember));
  }

  @Transactional
  public void deleteTeamMember(Long id) {
    TeamMembers teamMember = teamMembersRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Team member not found"));

    fileStorageService.deleteImage(teamMember.getImage());
    teamMembersRepository.delete(teamMember);
  }

  public List<TeamMemberResponseDto> getAllTeamMembers() {
    return teamMembersRepository.findAll()
        .stream()
        .map(TeamMemberMapper::toDto)
        .toList();
  }
}
