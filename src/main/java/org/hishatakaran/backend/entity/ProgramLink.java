package org.hishatakaran.backend.entity;

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
@Table(name = "program_links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramLink {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program", nullable = false)
  private Program program;

  private String titleHy;
  private String titleEn;
  private String titleFr;
  private String url;
  private String pdf;

  public ProgramLink(Program program, String titleHy, String titleEn, String titleFr, String url, String pdf) {
    this.program = program;
    this.titleHy = titleHy;
    this.titleEn = titleEn;
    this.titleFr = titleFr;
    this.url = url;
    this.pdf = pdf;
  }

  @Override
  public String toString() {
    return "Bibliography{" +
        "id=" + id +
        ", program=" + program +
        ", titleHy='" + titleHy + '\'' +
        ", titleEn='" + titleEn + '\'' +
        ", titleFr='" + titleFr + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
