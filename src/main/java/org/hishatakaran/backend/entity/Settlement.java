package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    /** Պահպանման միավորի անվանումը: the admin picks one of settlement_types. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_type")
    private SettlementType settlementType;

    /** Այլ անվանումներ */
    @Column(columnDefinition = "TEXT")
    private String anotherNamesHy;
    @Column(columnDefinition = "TEXT")
    private String anotherNamesEn;
    @Column(columnDefinition = "TEXT")
    private String anotherNamesFr;

    /** Պատմական զարգացումը և ժամանակագրությունը */
    @Column(columnDefinition = "TEXT")
    private String historyHy;
    @Column(columnDefinition = "TEXT")
    private String historyEn;
    @Column(columnDefinition = "TEXT")
    private String historyFr;

    @Column(columnDefinition = "TEXT")
    private String descriptionHy;
    @Column(columnDefinition = "TEXT")
    private String descriptionEn;
    @Column(columnDefinition = "TEXT")
    private String descriptionFr;

    /** Թարմացված տվյալներ: text with embedded photos, links and video links, so it can be long. */
    @Column(columnDefinition = "LONGTEXT")
    private String dataUpdateHy;
    @Column(columnDefinition = "LONGTEXT")
    private String dataUpdateEn;
    @Column(columnDefinition = "LONGTEXT")
    private String dataUpdateFr;

    /** Տեղագրական տեղեկանք, coordinates included. */
    @OneToOne(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    private SettlementTopographic topographics;

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettlementImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettlementMeasurement> measurements = new ArrayList<>();

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettlementVideo> videos = new ArrayList<>();

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettlementBibliography> bibliography = new ArrayList<>();

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
}
