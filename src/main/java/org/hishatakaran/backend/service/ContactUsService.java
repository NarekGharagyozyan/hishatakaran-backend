package org.hishatakaran.backend.service;

import java.util.Comparator;
import java.util.List;

import org.hishatakaran.backend.entity.ContactUs;
import org.hishatakaran.backend.mapper.ContactUsMapper;
import org.hishatakaran.backend.model.ContactUsRequestDto;
import org.hishatakaran.backend.model.ContactUsResponseDto;
import org.hishatakaran.backend.repository.ContactUsRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactUsService {

  private final ContactUsRepository contactUsRepository;

  public ContactUsResponseDto saveContactUs(ContactUsRequestDto contactUsRequestDto) {

    ContactUs savedContactUs = contactUsRepository.save(new ContactUs(
        contactUsRequestDto.getName(),
        contactUsRequestDto.getEmailOrPhoneNumber(),
        contactUsRequestDto.getMessage()
    ));
    return ContactUsMapper.toDto(savedContactUs);
  }

  public List<ContactUsResponseDto> getAllContactUs() {
    return contactUsRepository.findAll()
        .stream()
        .map(ContactUsMapper::toDto)
        .sorted(Comparator.comparing(ContactUsResponseDto::getSentTime).reversed())
        .toList();
  }
}
