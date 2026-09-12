package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.ContactUsRequestDto;
import org.hishatakaran.backend.model.ContactUsResponseDto;
import org.hishatakaran.backend.service.ContactUsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contactUs")
public class ContactUsController {

  private final ContactUsService contactUsService;

  @PostMapping
  public ResponseEntity<ContactUsResponseDto> contactUs(@RequestBody ContactUsRequestDto contactUsRequestDto)
  {
    return ResponseEntity.ok(contactUsService.saveContactUs(contactUsRequestDto));
  }

  @GetMapping
  public ResponseEntity<List<ContactUsResponseDto>> getAllContactUs() {
    return ResponseEntity.ok(contactUsService.getAllContactUs());
  }
}
