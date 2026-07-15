package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;

public class TeamMemberMapper {

  public static TeamMemberResponseDto toResponseDto(TeamMembers teamMembers)
  {
    return new TeamMemberResponseDto(
        LanguagesResponseDto.of(
            teamMembers.getNameHy(),
            teamMembers.getNameEn(),
            teamMembers.getNameFr()
        ),
        LanguagesResponseDto.of(
            teamMembers.getSurnameHy(),
            teamMembers.getSurnameEn(),
            teamMembers.getSurnameFr()
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
        teamMembers.getImage()
    );
  }
}
