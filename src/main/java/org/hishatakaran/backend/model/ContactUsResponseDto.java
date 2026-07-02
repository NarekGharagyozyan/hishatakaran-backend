package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ContactUsResponseDto {
  private final Long id;
  private final String name;
  private final String emailOrPhoneNumber;
  private final String message;
  private final Long sentTime;
}
