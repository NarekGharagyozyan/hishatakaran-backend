package org.hishatakaran.backend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hishatakaran.backend.model.MonumentType;
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
@Table(name = "monuments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Monument extends BaseEntity{

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String nameArmenian;
    private String nameEnglish;
    private String nameFrench;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement", nullable = false)
    private Settlement settlement;

    private String monumentType;

    private String specialNameArmenian;
    private String specialNameEnglish;
    private String specialNameFrench;

    @ElementCollection
    @CollectionTable(name = "monument_another_names_armenian", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "another_names_armenian")
    private List<String> anotherNamesArmenian = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "monument_another_names_english", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "another_names_english")
    private List<String> anotherNamesEnglish = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "monument_another_names_french", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "another_names_french")
    private List<String> anotherNamesFrench = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String historyArmenian;

    @Column(columnDefinition = "TEXT")
    private String historyEnglish;

    @Column(columnDefinition = "TEXT")
    private String historyFrench;

    private String originalAffiliationArmenian;
    private String originalAffiliationEnglish;
    private String originalAffiliationFrench;

    private String storageUnitNameArmenian;
    private String storageUnitNameEnglish;
    private String storageUnitNameFrench;

    @Column(name = "monument_condition_armenian")
    private String conditionArmenian;

    @Column(name = "monument_condition_english")
    private String conditionEnglish;

    @Column(name = "monument_condition_french")
    private String conditionFrench;

    @ElementCollection
    @CollectionTable(name = "monument_pictures", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "picture_url")
    private List<String> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bibliography> bibliography = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topographic> topographics = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricalReference> historicalReferences = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DescriptiveCharacteristicReference> descriptiveCharacteristics = new ArrayList<>();

    private String signature;

    public Monument(
        Status status,
        String nameArmenian,
        String nameEnglish,
        String nameFrench,
        Region region,
        Settlement settlement,
        String monumentType,
        String specialNameArmenian,
        String specialNameEnglish,
        String specialNameFrench,
        List<String> anotherNamesArmenian,
        List<String> anotherNamesEnglish,
        List<String> anotherNamesFrench,
        String historyArmenian,
        String historyEnglish,
        String historyFrench,
        String originalAffiliationArmenian,
        String originalAffiliationEnglish,
        String originalAffiliationFrench,
        String storageUnitNameArmenian,
        String storageUnitNameEnglish,
        String storageUnitNameFrench,
        String conditionArmenian,
        String conditionEnglish,
        String conditionFrench,
        List<String> pictures,
        List<Bibliography> bibliography,
        List<Topographic> topographics,
        List<HistoricalReference> historicalReferences,
        List<DescriptiveCharacteristicReference> descriptiveCharacteristics,
        String signature)
    {
        this.status = status;
        this.nameArmenian = nameArmenian;
        this.nameEnglish = nameEnglish;
        this.nameFrench = nameFrench;
        this.region = region;
        this.settlement = settlement;
        this.monumentType = monumentType;
        this.specialNameArmenian = specialNameArmenian;
        this.specialNameEnglish = specialNameEnglish;
        this.specialNameFrench = specialNameFrench;
        this.anotherNamesArmenian = anotherNamesArmenian;
        this.anotherNamesEnglish = anotherNamesEnglish;
        this.anotherNamesFrench = anotherNamesFrench;
        this.historyArmenian = historyArmenian;
        this.historyEnglish = historyEnglish;
        this.historyFrench = historyFrench;
        this.originalAffiliationArmenian = originalAffiliationArmenian;
        this.originalAffiliationEnglish = originalAffiliationEnglish;
        this.originalAffiliationFrench = originalAffiliationFrench;
        this.storageUnitNameArmenian = storageUnitNameArmenian;
        this.storageUnitNameEnglish = storageUnitNameEnglish;
        this.storageUnitNameFrench = storageUnitNameFrench;
        this.conditionArmenian = conditionArmenian;
        this.conditionEnglish = conditionEnglish;
        this.conditionFrench = conditionFrench;
        this.pictures = pictures;
        this.bibliography = bibliography;
        this.topographics = topographics;
        this.historicalReferences = historicalReferences;
        this.descriptiveCharacteristics = descriptiveCharacteristics;
        this.signature = signature;
    }
}