package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "monument_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonumentTypes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nameHy;
  @Column(nullable = false)
  private String nameEn;
  @Column(nullable = false)
  private String nameFr;

  @OneToMany(mappedBy = "monumentType")
  private List<Monument> monuments = new ArrayList<>();

  public MonumentTypes(String nameHy, String nameEn, String nameFr) {
    this.nameHy = nameHy;
    this.nameEn = nameEn;
    this.nameFr = nameFr;
  }
}
