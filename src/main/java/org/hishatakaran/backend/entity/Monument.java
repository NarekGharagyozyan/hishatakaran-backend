package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "monuments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {
    "region",
    "settlement",
    "monument_type",
    "videos",
    "bibliography",
    "topographics",
    "historicalReferences",
    "descriptiveCharacteristics"
})
public class Monument extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isPublished;

    @Column(columnDefinition = "TEXT")
    private String nameHy;
    @Column(columnDefinition = "TEXT")
    private String nameEn;
    @Column(columnDefinition = "TEXT")
    private String nameFr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement", nullable = false)
    private Settlement settlement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument_type", nullable = false)
    private MonumentTypes monumentType;

    @Column(columnDefinition = "TEXT")
    private String specialNameHy;
    @Column(columnDefinition = "TEXT")
    private String specialNameEn;
    @Column(columnDefinition = "TEXT")
    private String specialNameFr;

    @Column(columnDefinition = "TEXT")
    private String anotherNamesHy;
    @Column(columnDefinition = "TEXT")
    private String anotherNamesEn;
    @Column(columnDefinition = "TEXT")
    private String anotherNamesFr;

    @Column(columnDefinition = "TEXT")
    private String historyHy;
    @Column(columnDefinition = "TEXT")
    private String historyEn;
    @Column(columnDefinition = "TEXT")
    private String historyFr;

    @Column(columnDefinition = "TEXT")
    private String originalAffiliationHy;
    @Column(columnDefinition = "TEXT")
    private String originalAffiliationEn;
    @Column(columnDefinition = "TEXT")
    private String originalAffiliationFr;

    @Column(columnDefinition = "TEXT")
    private String storageUnitNameHy;
    @Column(columnDefinition = "TEXT")
    private String storageUnitNameEn;
    @Column(columnDefinition = "TEXT")
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
    private List<MonumentVideo> videos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "monument_measurements", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "measurement_url")
    private List<String> measurements = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderNumber ASC")
    private List<Footnote> footnotes = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bibliography> bibliography;

    @OneToOne(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private Topographic topographics;

    @OneToOne(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private HistoricalReference historicalReferences;

    @OneToOne(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private DescriptiveCharacteristicReference descriptiveCharacteristics;

    private String signature;
    private Boolean showInMainPage;

    public Monument(
        Boolean isPublished,
        String nameHy,
        String nameEn,
        String nameFr,
        Region region,
        Settlement settlement,
        MonumentTypes monumentType,
        String specialNameHy,
        String specialNameEn,
        String specialNameFr,
        String anotherNamesHy,
        String anotherNamesEn,
        String anotherNamesFr,
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
        List<MonumentVideo> videos,
        List<String> measurements,
        List<Bibliography> bibliography,
        Topographic topographics,
        HistoricalReference historicalReferences,
        DescriptiveCharacteristicReference descriptiveCharacteristics,
        String signature)
    {
        this.isPublished = isPublished;
        this.nameHy = nameHy;
        this.nameEn = nameEn;
        this.nameFr = nameFr;
        this.region = region;
        this.settlement = settlement;
        this.monumentType = monumentType;
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
        this.videos = videos;
        this.measurements = measurements;
        this.bibliography = bibliography;
        this.topographics = topographics;
        this.historicalReferences = historicalReferences;
        this.descriptiveCharacteristics = descriptiveCharacteristics;
        this.signature = signature;
    }
}