package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDto {

    private List<MonumentResponseDto> monuments;
    private List<ProgramCulturalHeritageDocumentationResponseDto> programCulturalHeritages;
    private List<ProgramResponseDto> programs;
    private List<ExhibitionResponseDto> exhibitions;
    private List<LibraryResponseDto> libraries;
    private List<TeamMemberResponseDto> teamMembers;
    private List<SettlementResponseDto> settlements;

}