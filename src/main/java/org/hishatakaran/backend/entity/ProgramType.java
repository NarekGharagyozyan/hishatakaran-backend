package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "program_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nameHy;
  @Column(nullable = false)
  private String nameEn;
  @Column(nullable = false)
  private String nameFr;

  @OneToMany(mappedBy = "programTypes")
  private List<Program> programs = new ArrayList<>();
}
