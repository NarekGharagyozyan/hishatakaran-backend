package org.hishatakaran.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact_us")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactUs extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String emailOrPhoneNumber;
  private String message;

  public ContactUs(String name, String emailOrPhoneNumber, String message) {
    this.name = name;
    this.emailOrPhoneNumber = emailOrPhoneNumber;
    this.message = message;
  }
}
