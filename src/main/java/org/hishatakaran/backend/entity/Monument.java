package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import org.hishatakaran.backend.model.Status;


import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "monuments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Monument extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String nameHy;
    private String nameEn;
    private String nameFr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement", nullable = false)
    private Settlement settlement;

    private String monumentTypeHy;
    private String monumentTypeEn;
    private String monumentTypeFr;

    private String specialNameHy;
    private String specialNameEn;
    private String specialNameFr;

    @ElementCollection
    @CollectionTable(name = "monument_another_names_hy", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "another_names_hy")
    private List<String> anotherNamesHy = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "monument_another_names_en", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "another_names_en")
    private List<String> anotherNamesEn = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "monument_another_names_fr", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "another_names_fr")
    private List<String> anotherNamesFr = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String historyHy;

    @Column(columnDefinition = "TEXT")
    private String historyEn;

    @Column(columnDefinition = "TEXT")
    private String historyFr;

    private String originalAffiliationHy;
    private String originalAffiliationEn;
    private String originalAffiliationFr;

    private String storageUnitNameHy;
    private String storageUnitNameEn;
    private String storageUnitNameFr;

    @Column(name = "monument_condition_hy")
    private String conditionHy;

    @Column(name = "monument_condition_en")
    private String conditionEn;

    @Column(name = "monument_condition_fr")
    private String conditionFr;

    @ElementCollection
    @CollectionTable(name = "monument_images", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bibliography> bibliography;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Topographic topographics;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private HistoricalReference historicalReferences;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private DescriptiveCharacteristicReference descriptiveCharacteristics;

    private String signature;

    public Monument(
        Status status,
        String nameHy,
        String nameEn,
        String nameFr,
        Region region,
        Settlement settlement,
        String monumentTypeHy,
        String monumentTypeEn,
        String monumentTypeFr,
        String specialNameHy,
        String specialNameEn,
        String specialNameFr,
        List<String> anotherNamesHy,
        List<String> anotherNamesEn,
        List<String> anotherNamesFr,
        String historyHy,
        String historyEn,
        String historyFr,
        String originalAffiliationHy,
        String originalAffiliationEn,
        String originalAffiliationFr,
        String storageUnitNameHy,
        String storageUnitNameEn,
        String storageUnitNameFr,
        String conditionHy,
        String conditionEn,
        String conditionFr,
        List<String> images,
        List<Bibliography> bibliography,
        Topographic topographics,
        HistoricalReference historicalReferences,
        DescriptiveCharacteristicReference descriptiveCharacteristics,
        String signature)
    {
        this.status = status;
        this.nameHy = nameHy;
        this.nameEn = nameEn;
        this.nameFr = nameFr;
        this.region = region;
        this.settlement = settlement;
        this.monumentTypeHy = monumentTypeHy;
        this.monumentTypeEn = monumentTypeEn;
        this.monumentTypeFr = monumentTypeFr;
        this.specialNameHy = specialNameHy;
        this.specialNameEn = specialNameEn;
        this.specialNameFr = specialNameFr;
        this.anotherNamesHy = anotherNamesHy;
        this.anotherNamesEn = anotherNamesEn;
        this.anotherNamesFr = anotherNamesFr;
        this.historyHy = historyHy;
        this.historyEn = historyEn;
        this.historyFr = historyFr;
        this.originalAffiliationHy = originalAffiliationHy;
        this.originalAffiliationEn = originalAffiliationEn;
        this.originalAffiliationFr = originalAffiliationFr;
        this.storageUnitNameHy = storageUnitNameHy;
        this.storageUnitNameEn = storageUnitNameEn;
        this.storageUnitNameFr = storageUnitNameFr;
        this.conditionHy = conditionHy;
        this.conditionEn = conditionEn;
        this.conditionFr = conditionFr;
        this.images = images;
        this.bibliography = bibliography;
        this.topographics = topographics;
        this.historicalReferences = historicalReferences;
        this.descriptiveCharacteristics = descriptiveCharacteristics;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Monument{" +
            "id=" + id +
            ", status=" + status +
            ", nameHy='" + nameHy + '\'' +
            ", nameEn='" + nameEn + '\'' +
            ", nameFr='" + nameFr + '\'' +
            ", region=" + region +
            ", settlement=" + settlement +
            ", monumentTypeHy='" + monumentTypeHy + '\'' +
            ", monumentTypeEn='" + monumentTypeEn + '\'' +
            ", monumentTypeFr='" + monumentTypeFr + '\'' +
            ", specialNameHy='" + specialNameHy + '\'' +
            ", specialNameEn='" + specialNameEn + '\'' +
            ", specialNameFr='" + specialNameFr + '\'' +
            ", anotherNamesHy=" + anotherNamesHy +
            ", anotherNamesEn=" + anotherNamesEn +
            ", anotherNamesFr=" + anotherNamesFr +
            ", historyHy='" + historyHy + '\'' +
            ", historyEn='" + historyEn + '\'' +
            ", historyFr='" + historyFr + '\'' +
            ", originalAffiliationHy='" + originalAffiliationHy + '\'' +
            ", originalAffiliationEn='" + originalAffiliationEn + '\'' +
            ", originalAffiliationFr='" + originalAffiliationFr + '\'' +
            ", storageUnitNameHy='" + storageUnitNameHy + '\'' +
            ", storageUnitNameEn='" + storageUnitNameEn + '\'' +
            ", storageUnitNameFr='" + storageUnitNameFr + '\'' +
            ", conditionHy='" + conditionHy + '\'' +
            ", conditionEn='" + conditionEn + '\'' +
            ", conditionFr='" + conditionFr + '\'' +
            ", images=" + images +
            ", bibliography=" + bibliography +
            ", topographics=" + topographics +
            ", historicalReferences=" + historicalReferences +
            ", descriptiveCharacteristics=" + descriptiveCharacteristics +
            ", signature='" + signature + '\'' +
            '}';
    }
}