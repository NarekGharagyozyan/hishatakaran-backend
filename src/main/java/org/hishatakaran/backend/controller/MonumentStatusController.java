package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.MonumentStatusResponseDto;
import org.hishatakaran.backend.service.MonumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/monumentStatus")
@RequiredArgsConstructor
public class MonumentStatusController {

  private final MonumentService monumentService;

  @GetMapping
  public List<MonumentStatusResponseDto> getAll() {
    return monumentService.getAllMonumentStatuses();
  }
}
