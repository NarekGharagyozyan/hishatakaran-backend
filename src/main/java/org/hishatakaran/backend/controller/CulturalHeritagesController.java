package org.hishatakaran.backend.controller;

import org.hishatakaran.backend.model.CulturalHeritagesEditDto;
import org.hishatakaran.backend.model.CulturalHeritagesRequestDto;
import org.hishatakaran.backend.model.CulturalHeritagesResponseDto;
import org.hishatakaran.backend.service.CulturalHeritagesService;
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
public class CulturalHeritagesController {

  private final CulturalHeritagesService culturalHeritagesService;

  @PostMapping("/admin/culturalHeritages")
  public ResponseEntity<CulturalHeritagesResponseDto> createCulturalHeritages(
      @RequestBody CulturalHeritagesRequestDto culturalHeritagesRequestDto
  ){
    return ResponseEntity.ok(culturalHeritagesService.createCulturalHeritages(culturalHeritagesRequestDto));
  }

  @GetMapping("/culturalHeritages")
  public ResponseEntity<CulturalHeritagesResponseDto> getCulturalHeritages() {
    return ResponseEntity.ok(culturalHeritagesService.getCulturalHeritages());
  }

  @PutMapping("/admin/culturalHeritages")
  public ResponseEntity<CulturalHeritagesResponseDto> editCulturalHeritages(
      @RequestBody CulturalHeritagesEditDto culturalHeritagesEditDto
  ) {
    return ResponseEntity.ok(culturalHeritagesService.editCulturalHeritages(culturalHeritagesEditDto));
  }
}
