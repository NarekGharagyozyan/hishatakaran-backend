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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "descriptive_characteristic_reference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescriptiveCharacteristicReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    @Column(columnDefinition = "TEXT")
    private String archeologicalOverviewStratigraphyFindingsHy;
    @Column(columnDefinition = "TEXT")
    private String archeologicalOverviewStratigraphyFindingsEn;
    @Column(columnDefinition = "TEXT")
    private String archeologicalOverviewStratigraphyFindingsFr;

    @Column(columnDefinition = "TEXT")
    private String architecturalOverviewHy;
    @Column(columnDefinition = "TEXT")
    private String architecturalOverviewEn;
    @Column(columnDefinition = "TEXT")
    private String architecturalOverviewFr;

    @Column(columnDefinition = "TEXT")
    private String decorativeAndMonumentalFeaturesCompositionColoursHy;
    @Column(columnDefinition = "TEXT")
    private String decorativeAndMonumentalFeaturesCompositionColoursEn;
    @Column(columnDefinition = "TEXT")
    private String decorativeAndMonumentalFeaturesCompositionColoursFr;

    @Column(columnDefinition = "TEXT")
    private String theBuildingMaterialHy;
    @Column(columnDefinition = "TEXT")
    private String theBuildingMaterialEn;
    @Column(columnDefinition = "TEXT")
    private String theBuildingMaterialFr;

    @Column(columnDefinition = "TEXT")
    private String openingsEntrancesHy;
    @Column(columnDefinition = "TEXT")
    private String openingsEntrancesEn;
    @Column(columnDefinition = "TEXT")
    private String openingsEntrancesFr;

    @Column(columnDefinition = "TEXT")
    private String openingsWindowsHy;
    @Column(columnDefinition = "TEXT")
    private String openingsWindowsEn;
    @Column(columnDefinition = "TEXT")
    private String openingsWindowsFr;

    @Column(columnDefinition = "TEXT")
    private String levelsOfConstructionHy;
    @Column(columnDefinition = "TEXT")
    private String levelsOfConstructionEn;
    @Column(columnDefinition = "TEXT")
    private String levelsOfConstructionFr;

    @Column(columnDefinition = "TEXT")
    private String constructionsHy;
    @Column(columnDefinition = "TEXT")
    private String constructionsEn;
    @Column(columnDefinition = "TEXT")
    private String constructionsFr;

    @Column(columnDefinition = "TEXT")
    private String roofHy;
    @Column(columnDefinition = "TEXT")
    private String roofEn;
    @Column(columnDefinition = "TEXT")
    private String roofFr;

    @Column(columnDefinition = "TEXT")
    private String typeHy;
    @Column(columnDefinition = "TEXT")
    private String typeEn;
    @Column(columnDefinition = "TEXT")
    private String typeFr;

    @Column(columnDefinition = "TEXT")
    private String exteriorHy;
    @Column(columnDefinition = "TEXT")
    private String exteriorEn;
    @Column(columnDefinition = "TEXT")
    private String exteriorFr;

    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueHy;
    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueEn;
    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueFr;

    @Column(columnDefinition = "TEXT")
    private String lengthHy;
    @Column(columnDefinition = "TEXT")
    private String lengthEn;
    @Column(columnDefinition = "TEXT")
    private String lengthFr;

    @Column(columnDefinition = "TEXT")
    private String widthHy;
    @Column(columnDefinition = "TEXT")
    private String widthEn;
    @Column(columnDefinition = "TEXT")
    private String widthFr;

    @Column(columnDefinition = "TEXT")
    private String heightHy;
    @Column(columnDefinition = "TEXT")
    private String heightEn;
    @Column(columnDefinition = "TEXT")
    private String heightFr;

    @Column(columnDefinition = "TEXT")
    private String depthThicknessHy;
    @Column(columnDefinition = "TEXT")
    private String depthThicknessEn;
    @Column(columnDefinition = "TEXT")
    private String depthThicknessFr;

    @Column(columnDefinition = "TEXT")
    private String areaHy;
    @Column(columnDefinition = "TEXT")
    private String areaEn;
    @Column(columnDefinition = "TEXT")
    private String areaFr;

    @Column(columnDefinition = "TEXT")
    private String lengthOfSpanHy;
    @Column(columnDefinition = "TEXT")
    private String lengthOfSpanEn;
    @Column(columnDefinition = "TEXT")
    private String lengthOfSpanFr;

    @Column(columnDefinition = "TEXT")
    private String stateOfMonumentHy;
    @Column(columnDefinition = "TEXT")
    private String stateOfMonumentEn;
    @Column(columnDefinition = "TEXT")
    private String stateOfMonumentFr;

    @Column(columnDefinition = "TEXT")
    private String valuationHy;
    @Column(columnDefinition = "TEXT")
    private String valuationEn;
    @Column(columnDefinition = "TEXT")
    private String valuationFr;

    public DescriptiveCharacteristicReference(Monument monument, String archeologicalOverviewStratigraphyFindingsHy,
        String archeologicalOverviewStratigraphyFindingsEn, String archeologicalOverviewStratigraphyFindingsFr,
        String architecturalOverviewHy, String architecturalOverviewEn, String architecturalOverviewFr,
        String decorativeAndMonumentalFeaturesCompositionColoursHy,
        String decorativeAndMonumentalFeaturesCompositionColoursEn,
        String decorativeAndMonumentalFeaturesCompositionColoursFr, String theBuildingMaterialHy,
        String theBuildingMaterialEn, String theBuildingMaterialFr, String openingsEntrancesHy,
        String openingsEntrancesEn,
        String openingsEntrancesFr, String openingsWindowsHy, String openingsWindowsEn, String openingsWindowsFr,
        String levelsOfConstructionHy, String levelsOfConstructionEn, String levelsOfConstructionFr,
        String constructionsHy,
        String constructionsEn, String constructionsFr, String roofHy, String roofEn, String roofFr, String typeHy,
        String typeEn, String typeFr, String exteriorHy, String exteriorEn, String exteriorFr,
        String implementationTechniqueHy, String implementationTechniqueEn, String implementationTechniqueFr,
        String lengthHy, String lengthEn, String lengthFr, String widthHy, String widthEn, String widthFr,
        String heightHy,
        String heightEn, String heightFr, String depthThicknessHy, String depthThicknessEn, String depthThicknessFr,
        String areaHy, String areaEn, String areaFr, String lengthOfSpanHy, String lengthOfSpanEn,
        String lengthOfSpanFr,
        String stateOfMonumentHy, String stateOfMonumentEn, String stateOfMonumentFr, String valuationHy,
        String valuationEn, String valuationFr
    )
    {
        this.monument = monument;
        this.archeologicalOverviewStratigraphyFindingsHy = archeologicalOverviewStratigraphyFindingsHy;
        this.archeologicalOverviewStratigraphyFindingsEn = archeologicalOverviewStratigraphyFindingsEn;
        this.archeologicalOverviewStratigraphyFindingsFr = archeologicalOverviewStratigraphyFindingsFr;
        this.architecturalOverviewHy = architecturalOverviewHy;
        this.architecturalOverviewEn = architecturalOverviewEn;
        this.architecturalOverviewFr = architecturalOverviewFr;
        this.decorativeAndMonumentalFeaturesCompositionColoursHy = decorativeAndMonumentalFeaturesCompositionColoursHy;
        this.decorativeAndMonumentalFeaturesCompositionColoursEn = decorativeAndMonumentalFeaturesCompositionColoursEn;
        this.decorativeAndMonumentalFeaturesCompositionColoursFr = decorativeAndMonumentalFeaturesCompositionColoursFr;
        this.theBuildingMaterialHy = theBuildingMaterialHy;
        this.theBuildingMaterialEn = theBuildingMaterialEn;
        this.theBuildingMaterialFr = theBuildingMaterialFr;
        this.openingsEntrancesHy = openingsEntrancesHy;
        this.openingsEntrancesEn = openingsEntrancesEn;
        this.openingsEntrancesFr = openingsEntrancesFr;
        this.openingsWindowsHy = openingsWindowsHy;
        this.openingsWindowsEn = openingsWindowsEn;
        this.openingsWindowsFr = openingsWindowsFr;
        this.levelsOfConstructionHy = levelsOfConstructionHy;
        this.levelsOfConstructionEn = levelsOfConstructionEn;
        this.levelsOfConstructionFr = levelsOfConstructionFr;
        this.constructionsHy = constructionsHy;
        this.constructionsEn = constructionsEn;
        this.constructionsFr = constructionsFr;
        this.roofHy = roofHy;
        this.roofEn = roofEn;
        this.roofFr = roofFr;
        this.typeHy = typeHy;
        this.typeEn = typeEn;
        this.typeFr = typeFr;
        this.exteriorHy = exteriorHy;
        this.exteriorEn = exteriorEn;
        this.exteriorFr = exteriorFr;
        this.implementationTechniqueHy = implementationTechniqueHy;
        this.implementationTechniqueEn = implementationTechniqueEn;
        this.implementationTechniqueFr = implementationTechniqueFr;
        this.lengthHy = lengthHy;
        this.lengthEn = lengthEn;
        this.lengthFr = lengthFr;
        this.widthHy = widthHy;
        this.widthEn = widthEn;
        this.widthFr = widthFr;
        this.heightHy = heightHy;
        this.heightEn = heightEn;
        this.heightFr = heightFr;
        this.depthThicknessHy = depthThicknessHy;
        this.depthThicknessEn = depthThicknessEn;
        this.depthThicknessFr = depthThicknessFr;
        this.areaHy = areaHy;
        this.areaEn = areaEn;
        this.areaFr = areaFr;
        this.lengthOfSpanHy = lengthOfSpanHy;
        this.lengthOfSpanEn = lengthOfSpanEn;
        this.lengthOfSpanFr = lengthOfSpanFr;
        this.stateOfMonumentHy = stateOfMonumentHy;
        this.stateOfMonumentEn = stateOfMonumentEn;
        this.stateOfMonumentFr = stateOfMonumentFr;
        this.valuationHy = valuationHy;
        this.valuationEn = valuationEn;
        this.valuationFr = valuationFr;
    }

    @Override
    public String toString() {
        return "DescriptiveCharacteristicReference{" +
            "id=" + id +
            ", monument=" + monument +
            ", archeologicalOverviewStratigraphyFindingsHy='" + archeologicalOverviewStratigraphyFindingsHy + '\'' +
            ", archeologicalOverviewStratigraphyFindingsEn='" + archeologicalOverviewStratigraphyFindingsEn + '\'' +
            ", archeologicalOverviewStratigraphyFindingsFr='" + archeologicalOverviewStratigraphyFindingsFr + '\'' +
            ", architecturalOverviewHy='" + architecturalOverviewHy + '\'' +
            ", architecturalOverviewEn='" + architecturalOverviewEn + '\'' +
            ", architecturalOverviewFr='" + architecturalOverviewFr + '\'' +
            ", decorativeAndMonumentalFeaturesCompositionColoursHy='" + decorativeAndMonumentalFeaturesCompositionColoursHy + '\'' +
            ", decorativeAndMonumentalFeaturesCompositionColoursEn='" + decorativeAndMonumentalFeaturesCompositionColoursEn + '\'' +
            ", decorativeAndMonumentalFeaturesCompositionColoursFr='" + decorativeAndMonumentalFeaturesCompositionColoursFr + '\'' +
            ", theBuildingMaterialHy='" + theBuildingMaterialHy + '\'' +
            ", theBuildingMaterialEn='" + theBuildingMaterialEn + '\'' +
            ", theBuildingMaterialFr='" + theBuildingMaterialFr + '\'' +
            ", openingsEntrancesHy='" + openingsEntrancesHy + '\'' +
            ", openingsEntrancesEn='" + openingsEntrancesEn + '\'' +
            ", openingsEntrancesFr='" + openingsEntrancesFr + '\'' +
            ", openingsWindowsHy='" + openingsWindowsHy + '\'' +
            ", openingsWindowsEn='" + openingsWindowsEn + '\'' +
            ", openingsWindowsFr='" + openingsWindowsFr + '\'' +
            ", levelsOfConstructionHy='" + levelsOfConstructionHy + '\'' +
            ", levelsOfConstructionEn='" + levelsOfConstructionEn + '\'' +
            ", levelsOfConstructionFr='" + levelsOfConstructionFr + '\'' +
            ", constructionsHy='" + constructionsHy + '\'' +
            ", constructionsEn='" + constructionsEn + '\'' +
            ", constructionsFr='" + constructionsFr + '\'' +
            ", roofHy='" + roofHy + '\'' +
            ", roofEn='" + roofEn + '\'' +
            ", roofFr='" + roofFr + '\'' +
            ", typeHy='" + typeHy + '\'' +
            ", typeEn='" + typeEn + '\'' +
            ", typeFr='" + typeFr + '\'' +
            ", exteriorHy='" + exteriorHy + '\'' +
            ", exteriorEn='" + exteriorEn + '\'' +
            ", exteriorFr='" + exteriorFr + '\'' +
            ", implementationTechniqueHy='" + implementationTechniqueHy + '\'' +
            ", implementationTechniqueEn='" + implementationTechniqueEn + '\'' +
            ", implementationTechniqueFr='" + implementationTechniqueFr + '\'' +
            ", lengthHy='" + lengthHy + '\'' +
            ", lengthEn='" + lengthEn + '\'' +
            ", lengthFr='" + lengthFr + '\'' +
            ", widthHy='" + widthHy + '\'' +
            ", widthEn='" + widthEn + '\'' +
            ", widthFr='" + widthFr + '\'' +
            ", heightHy='" + heightHy + '\'' +
            ", heightEn='" + heightEn + '\'' +
            ", heightFr='" + heightFr + '\'' +
            ", depthThicknessHy='" + depthThicknessHy + '\'' +
            ", depthThicknessEn='" + depthThicknessEn + '\'' +
            ", depthThicknessFr='" + depthThicknessFr + '\'' +
            ", areaHy='" + areaHy + '\'' +
            ", areaEn='" + areaEn + '\'' +
            ", areaFr='" + areaFr + '\'' +
            ", lengthOfSpanHy='" + lengthOfSpanHy + '\'' +
            ", lengthOfSpanEn='" + lengthOfSpanEn + '\'' +
            ", lengthOfSpanFr='" + lengthOfSpanFr + '\'' +
            ", stateOfMonumentHy='" + stateOfMonumentHy + '\'' +
            ", stateOfMonumentEn='" + stateOfMonumentEn + '\'' +
            ", stateOfMonumentFr='" + stateOfMonumentFr + '\'' +
            ", valuationHy='" + valuationHy + '\'' +
            ", valuationEn='" + valuationEn + '\'' +
            ", valuationFr='" + valuationFr + '\'' +
            '}';
    }
}