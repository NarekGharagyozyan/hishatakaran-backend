package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.ContactUs;
import org.hishatakaran.backend.model.ContactUsResponseDto;

public class ContactUsMapper {

  public static ContactUsResponseDto toDto(ContactUs contactUs) {
    return new ContactUsResponseDto(
        contactUs.getId(),
        contactUs.getName(),
        contactUs.getEmailOrPhoneNumber(),
        contactUs.getMessage(),
        contactUs.getCreatedAt().toEpochSecond()
    );
  }
}
