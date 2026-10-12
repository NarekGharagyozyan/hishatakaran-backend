package org.hishatakaran.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Կանխադրված տեսակներ՝ քաղաք, ավան, գյուղ, գյուղատեղի, բնակատեղի. seeded in V20. */
@Entity
@Table(name = "settlement_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nameHy;
  @Column(nullable = false)
  private String nameEn;
  @Column(nullable = false)
  private String nameFr;

  public SettlementType(String nameHy, String nameEn, String nameFr) {
    this.nameHy = nameHy;
    this.nameEn = nameEn;
    this.nameFr = nameFr;
  }
}
