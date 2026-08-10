package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.mapper.ProgramTypeMapper;
import org.hishatakaran.backend.model.ProgramTypeResponseDto;
import org.hishatakaran.backend.repository.ProgramTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programTypes")
@RequiredArgsConstructor
public class ProgramTypesController {

  private final ProgramTypeRepository programTypeRepository;

  @GetMapping
  public List<ProgramTypeResponseDto> getAll() {
    return programTypeRepository.findAll()
        .stream()
        .map(ProgramTypeMapper::toDto)
        .toList();
  }
}
