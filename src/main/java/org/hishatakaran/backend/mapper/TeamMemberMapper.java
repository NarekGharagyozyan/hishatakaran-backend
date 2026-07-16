package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;

public class TeamMemberMapper {

  public static TeamMemberResponseDto toDto(TeamMembers teamMembers)
  {
    return new TeamMemberResponseDto(
        teamMembers.getId(),
        LanguagesResponseDto.of(
            teamMembers.getFullNameHy(),
            teamMembers.getFullNameEn(),
            teamMembers.getFullNameFr()
        ),
        LanguagesResponseDto.of(
            teamMembers.getDescriptionHy(),
            teamMembers.getDescriptionEn(),
            teamMembers.getDescriptionFr()
        ),
        LanguagesResponseDto.of(
            teamMembers.getPositionHy(),
            teamMembers.getPositionEn(),
            teamMembers.getPositionFr()
        ),
        teamMembers.getUrl(),
        teamMembers.getSignature(),
        teamMembers.getImage()
    );
  }
}
