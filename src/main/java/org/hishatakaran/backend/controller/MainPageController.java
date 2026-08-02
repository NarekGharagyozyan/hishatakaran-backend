package org.hishatakaran.backend.controller;

import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.MainPageEditDto;
import org.hishatakaran.backend.model.MainPageRequestDto;
import org.hishatakaran.backend.model.MainPageResponseDto;
import org.hishatakaran.backend.model.QuantityResponseDto;
import org.hishatakaran.backend.service.MainPageService;
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
public class MainPageController {

  private final MainPageService mainPageService;

  @PostMapping("/admin/mainPage")
  public ResponseEntity<MainPageResponseDto> editMainPage(
      @RequestBody MainPageRequestDto mainPageRequestDto
  ){
    return ResponseEntity.ok(mainPageService.editMainPage(mainPageRequestDto));
  }

  @GetMapping("/mainPage")
  public ResponseEntity<MainPageResponseDto> editMainPage() {
    return ResponseEntity.ok(mainPageService.getMainPage());
  }

  @PutMapping("/admin/mainPage")
  public ResponseEntity<MainPageResponseDto> editMainPage(
      @RequestBody MainPageEditDto mainPageEditDto
  ) {
    return ResponseEntity.ok(mainPageService.editMainPage(mainPageEditDto));
  }

  @GetMapping("/quantity")
  public ResponseEntity<QuantityResponseDto> getQuantity() {
    return ResponseEntity.ok(mainPageService.getQuantity());
  }
}
