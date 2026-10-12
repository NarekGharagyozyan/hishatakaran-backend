package org.hishatakaran.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "settlement_bibliography")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementBibliography {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "settlement_id", nullable = false)
  private Settlement settlement;

  @Column(columnDefinition = "TEXT")
  private String titleHy;
  @Column(columnDefinition = "TEXT")
  private String titleEn;
  @Column(columnDefinition = "TEXT")
  private String titleFr;
  @Column(columnDefinition = "TEXT")
  private String url;

  public SettlementBibliography(String titleHy, String titleEn, String titleFr, String url, Settlement settlement) {
    this.titleHy = titleHy;
    this.titleEn = titleEn;
    this.titleFr = titleFr;
    this.url = url;
    this.settlement = settlement;
  }
}
