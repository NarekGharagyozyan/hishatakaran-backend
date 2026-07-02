package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.entity.ContactUs;
import org.hishatakaran.backend.mapper.ContactUsMapper;
import org.hishatakaran.backend.model.ContactUsRequestDto;
import org.hishatakaran.backend.model.ContactUsResponseDto;
import org.hishatakaran.backend.repository.ContactUsRepository;
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

  private final ContactUsRepository contactUsRepository;

  @PostMapping
  public ResponseEntity<ContactUsResponseDto> contactUs(@RequestBody ContactUsRequestDto contactUsRequestDto)
  {
    ContactUs savedContactUs = contactUsRepository.save(new ContactUs(
        contactUsRequestDto.getName(),
        contactUsRequestDto.getEmailOrPhoneNumber(),
        contactUsRequestDto.getMessage()
    ));
    return ResponseEntity.ok(ContactUsMapper.toDto(savedContactUs));
  }

  @GetMapping
  public ResponseEntity<List<ContactUsResponseDto>> getAllContactUs() {
    return ResponseEntity.ok(contactUsRepository.findAll()
        .stream()
        .map(ContactUsMapper::toDto)
        .toList());
  }
}
