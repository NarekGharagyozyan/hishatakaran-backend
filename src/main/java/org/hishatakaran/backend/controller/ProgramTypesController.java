package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.ProgramTypeResponseDto;
import org.hishatakaran.backend.service.ProgramService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programTypes")
@RequiredArgsConstructor
public class ProgramTypesController {

  private final ProgramService programService;

  @GetMapping
  public List<ProgramTypeResponseDto> getAll() {
    return programService.getAllProgramTypes();
  }
}
