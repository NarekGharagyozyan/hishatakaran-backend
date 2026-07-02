package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;

public class TeamMemberMapper {

  public static TeamMemberResponseDto toResponseDto(TeamMembers teamMembers)
  {
    return new TeamMemberResponseDto(
        new LanguagesResponseDto(
            teamMembers.getNameHy(),
            teamMembers.getNameEn(),
            teamMembers.getNameFr()
        ),
        new LanguagesResponseDto(
            teamMembers.getSurnameHy(),
            teamMembers.getSurnameEn(),
            teamMembers.getSurnameFr()
        ),
        new LanguagesResponseDto(
            teamMembers.getDescriptionHy(),
            teamMembers.getDescriptionEn(),
            teamMembers.getDescriptionFr()
        ),
        new LanguagesResponseDto(
            teamMembers.getPositionHy(),
            teamMembers.getPositionEn(),
            teamMembers.getPositionFr()
        ),
        teamMembers.getUrl(),
        teamMembers.getImage()
    );
  }
}
