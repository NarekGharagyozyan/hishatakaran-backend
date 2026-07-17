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
@Table(name = "settlements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameHy;
    @Column(nullable = false)
    private String nameEn;
    @Column(nullable = false)
    private String nameFr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = false)
    @JsonBackReference
    private Region region;

    @OneToMany(mappedBy = "settlement")
    private List<Monument> monuments = new ArrayList<>();

    @Override
    public String toString() {
        return "Settlement{" +
            "id=" + id +
            ", nameHy='" + nameHy + '\'' +
            ", nameEn='" + nameEn + '\'' +
            ", nameFr='" + nameFr + '\'' +
            ", region=" + region +
            '}';
    }

    public Settlement(String nameHy, String nameEn, String nameFr, Region region) {
        this.nameHy = nameHy;
        this.nameEn = nameEn;
        this.nameFr = nameFr;
        this.region = region;
    }
}