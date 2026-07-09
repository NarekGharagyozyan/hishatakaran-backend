package org.hishatakaran.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String theBuildingMaterialHy;
    private String theBuildingMaterialEn;
    private String theBuildingMaterialFr;

    private String openingsEntrancesHy;
    private String openingsEntrancesEn;
    private String openingsEntrancesFr;

    private String constructionsHy;
    private String constructionsEn;
    private String constructionsFr;

    private String roofHy;
    private String roofEn;
    private String roofFr;

    private String typeHy;
    private String typeEn;
    private String typeFr;

    private String colorHy;
    private String colorEn;
    private String colorFr;

    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueHy;
    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueEn;
    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueFr;

    private String stateOfMonumentHy;
    private String stateOfMonumentEn;
    private String stateOfMonumentFr;

    @Column(columnDefinition = "TEXT")
    private String valuationHy;
    @Column(columnDefinition = "TEXT")
    private String valuationEn;
    @Column(columnDefinition = "TEXT")
    private String valuationFr;

    public DescriptiveCharacteristicReference(Monument monument, String theBuildingMaterialHy,
        String theBuildingMaterialEn,
        String theBuildingMaterialFr, String openingsEntrancesHy, String openingsEntrancesEn,
        String openingsEntrancesFr,
        String constructionsHy, String constructionsEn, String constructionsFr, String roofHy, String roofEn,
        String roofFr,
        String typeHy, String typeEn, String typeFr, String colorHy, String colorEn, String colorFr,
        String implementationTechniqueHy, String implementationTechniqueEn, String implementationTechniqueFr,
        String stateOfMonumentHy, String stateOfMonumentEn, String stateOfMonumentFr, String valuationHy,
        String valuationEn, String valuationFr) {
        this.monument = monument;
        this.theBuildingMaterialHy = theBuildingMaterialHy;
        this.theBuildingMaterialEn = theBuildingMaterialEn;
        this.theBuildingMaterialFr = theBuildingMaterialFr;
        this.openingsEntrancesHy = openingsEntrancesHy;
        this.openingsEntrancesEn = openingsEntrancesEn;
        this.openingsEntrancesFr = openingsEntrancesFr;
        this.constructionsHy = constructionsHy;
        this.constructionsEn = constructionsEn;
        this.constructionsFr = constructionsFr;
        this.roofHy = roofHy;
        this.roofEn = roofEn;
        this.roofFr = roofFr;
        this.typeHy = typeHy;
        this.typeEn = typeEn;
        this.typeFr = typeFr;
        this.colorHy = colorHy;
        this.colorEn = colorEn;
        this.colorFr = colorFr;
        this.implementationTechniqueHy = implementationTechniqueHy;
        this.implementationTechniqueEn = implementationTechniqueEn;
        this.implementationTechniqueFr = implementationTechniqueFr;
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
            ", theBuildingMaterialHy='" + theBuildingMaterialHy + '\'' +
            ", theBuildingMaterialEn='" + theBuildingMaterialEn + '\'' +
            ", theBuildingMaterialFr='" + theBuildingMaterialFr + '\'' +
            ", openingsEntrancesHy='" + openingsEntrancesHy + '\'' +
            ", openingsEntrancesEn='" + openingsEntrancesEn + '\'' +
            ", openingsEntrancesFr='" + openingsEntrancesFr + '\'' +
            ", constructionsHy='" + constructionsHy + '\'' +
            ", constructionsEn='" + constructionsEn + '\'' +
            ", constructionsFr='" + constructionsFr + '\'' +
            ", roofHy='" + roofHy + '\'' +
            ", roofEn='" + roofEn + '\'' +
            ", roofFr='" + roofFr + '\'' +
            ", typeHy='" + typeHy + '\'' +
            ", typeEn='" + typeEn + '\'' +
            ", typeFr='" + typeFr + '\'' +
            ", colorHy='" + colorHy + '\'' +
            ", colorEn='" + colorEn + '\'' +
            ", colorFr='" + colorFr + '\'' +
            ", implementationTechniqueHy='" + implementationTechniqueHy + '\'' +
            ", implementationTechniqueEn='" + implementationTechniqueEn + '\'' +
            ", implementationTechniqueFr='" + implementationTechniqueFr + '\'' +
            ", stateOfMonumentHy='" + stateOfMonumentHy + '\'' +
            ", stateOfMonumentEn='" + stateOfMonumentEn + '\'' +
            ", stateOfMonumentFr='" + stateOfMonumentFr + '\'' +
            ", valuationHy='" + valuationHy + '\'' +
            ", valuationEn='" + valuationEn + '\'' +
            ", valuationFr='" + valuationFr + '\'' +
            '}';
    }
}