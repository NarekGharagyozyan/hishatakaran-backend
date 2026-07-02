package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.TeamMemberResponseDto;
import org.hishatakaran.backend.service.TeamMembersService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class TeamMembersController {

  private final TeamMembersService teamMembersService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<TeamMemberResponseDto> uploadFiles(
      @RequestPart("data") String teamMemberRequestDto,
      @RequestPart("image") MultipartFile image
  ){
    return ResponseEntity.ok(teamMembersService.addNewTeamMember(teamMemberRequestDto, image));
  }

  @GetMapping
  public ResponseEntity<List<TeamMemberResponseDto>> getAllMembers(){
    return ResponseEntity.ok(teamMembersService.getAllTeamMembers());
  }
}
