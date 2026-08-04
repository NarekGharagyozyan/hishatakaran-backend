package org.hishatakaran.backend.controller;

import org.hishatakaran.backend.model.AboutUsEditDto;
import org.hishatakaran.backend.model.AboutUsRequestDto;
import org.hishatakaran.backend.model.AboutUsResponseDto;
import org.hishatakaran.backend.service.AboutUsService;
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
public class AboutUsController {

  private final AboutUsService aboutUsService;

  @PostMapping("/admin/aboutUs")
  public ResponseEntity<AboutUsResponseDto> createAboutUs(
      @RequestBody AboutUsRequestDto mainPageRequestDto
  ){
    return ResponseEntity.ok(aboutUsService.createAboutUs(mainPageRequestDto));
  }

  @GetMapping("/aboutUs")
  public ResponseEntity<AboutUsResponseDto> getAboutUs() {
    return ResponseEntity.ok(aboutUsService.getAboutUs());
  }

  @PutMapping("/admin/aboutUs")
  public ResponseEntity<AboutUsResponseDto> editAboutUs(
      @RequestBody AboutUsEditDto aboutUsEditDto
  ) {
    return ResponseEntity.ok(aboutUsService.editAboutUs(aboutUsEditDto));
  }

}

