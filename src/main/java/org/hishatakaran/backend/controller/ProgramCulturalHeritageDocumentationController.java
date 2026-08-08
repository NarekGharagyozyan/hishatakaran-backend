package org.hishatakaran.backend.controller;

import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationEditDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationRequestDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationResponseDto;
import org.hishatakaran.backend.service.ProgramCulturalHeritageDocumentationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgramCulturalHeritageDocumentationController {

  private final ProgramCulturalHeritageDocumentationService programCulturalHeritageDocumentationService;

  @PostMapping("/admin/program/culturalHeritagesDocumentation")
  public ResponseEntity<ProgramCulturalHeritageDocumentationResponseDto> createProgramCulturalHeritageDocumentation(
      @RequestBody ProgramCulturalHeritageDocumentationRequestDto culturalHeritagesDocumentRequestDto
  ){
    return ResponseEntity.ok(programCulturalHeritageDocumentationService.createProgramCulturalHeritageDocumentation(culturalHeritagesDocumentRequestDto));
  }

  @GetMapping("/program/culturalHeritagesDocumentation")
  public ResponseEntity<ProgramCulturalHeritageDocumentationResponseDto> getProgramCulturalHeritageDocumentation() {
    return ResponseEntity.ok(programCulturalHeritageDocumentationService.getProgramCulturalHeritageDocumentation());
  }

  @PutMapping("/admin/program/culturalHeritagesDocumentation")
  public ResponseEntity<ProgramCulturalHeritageDocumentationResponseDto> editProgramCulturalHeritageDocumentation(
      @RequestBody ProgramCulturalHeritageDocumentationEditDto culturalHeritagesDocumentEditDto
  ) {
    return ResponseEntity.ok(programCulturalHeritageDocumentationService.editProgramCulturalHeritageDocumentation(culturalHeritagesDocumentEditDto));
  }

}
