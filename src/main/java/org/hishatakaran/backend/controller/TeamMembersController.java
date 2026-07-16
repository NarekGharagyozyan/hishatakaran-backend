package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.LibraryEditDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.TeamMemberEditDto;
import org.hishatakaran.backend.model.TeamMemberRequestDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.service.TeamMembersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamMembersController {

  private final TeamMembersService teamMembersService;

  @PostMapping("/admin/members")
  public ResponseEntity<TeamMemberResponseDto> addNewTeamMember(
      @RequestBody TeamMemberRequestDto teamMemberRequestDto
  ){
    return ResponseEntity.ok(teamMembersService.addNewTeamMember(teamMemberRequestDto));
  }

  @PostMapping("/admin/members/{id}/translate/{language}")
  public TeamMemberResponseDto translate(
      @PathVariable Long id,
      @PathVariable TranslationLanguage language
  ) {
    return teamMembersService.translate(id, language);
  }

  @PutMapping("/admin/members/{id}")
  public TeamMemberResponseDto editTeamMembers(
      @PathVariable Long id,
      @RequestBody TeamMemberEditDto teamMemberEditDto
  ) {
    return teamMembersService.editTeamMemberInformation(id, teamMemberEditDto);
  }

  @DeleteMapping("/admin/members/{id}")
  public void deleteTeamMember(@PathVariable Long id) {
    teamMembersService.deleteTeamMember(id);
  }

  @GetMapping("/members")
  public ResponseEntity<List<TeamMemberResponseDto>> getAllMembers(){
    return ResponseEntity.ok(teamMembersService.getAllTeamMembers());
  }
}
