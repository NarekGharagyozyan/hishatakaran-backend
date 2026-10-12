package org.hishatakaran.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Տեղագրական տեղեկանք */
@Entity
@Table(name = "settlement_topographics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementTopographic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "settlement_id", nullable = false)
  private Settlement settlement;

  /** Պատմա-աշխարհագրական բնութագիրը */
  @Column(columnDefinition = "TEXT")
  private String topographyHy;
  @Column(columnDefinition = "TEXT")
  private String topographyEn;
  @Column(columnDefinition = "TEXT")
  private String topographyFr;

  /** Հասցե */
  @Column(columnDefinition = "TEXT")
  private String addressHy;
  @Column(columnDefinition = "TEXT")
  private String addressEn;
  @Column(columnDefinition = "TEXT")
  private String addressFr;

  /** Կոորդինատներ */
  private String latitude;
  private String longitude;

  /** Տեղանքի բարձրությունը ծովի մակերևույթից */
  @Column(columnDefinition = "TEXT")
  private String altitudeHy;
  @Column(columnDefinition = "TEXT")
  private String altitudeEn;
  @Column(columnDefinition = "TEXT")
  private String altitudeFr;

  /** Ջրագրություն */
  @Column(columnDefinition = "TEXT")
  private String hydrographyHy;
  @Column(columnDefinition = "TEXT")
  private String hydrographyEn;
  @Column(columnDefinition = "TEXT")
  private String hydrographyFr;

  /** Թարմացված տվյալներ: text with embedded photos, links and video links, so it can be long. */
  @Column(columnDefinition = "LONGTEXT")
  private String dataUpdateHy;
  @Column(columnDefinition = "LONGTEXT")
  private String dataUpdateEn;
  @Column(columnDefinition = "LONGTEXT")
  private String dataUpdateFr;
}
