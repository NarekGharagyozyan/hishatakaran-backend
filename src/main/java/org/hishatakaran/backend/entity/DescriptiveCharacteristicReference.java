package org.hishatakaran.backend.entity;

import java.util.UUID;

import jakarta.persistence.Column;
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
@Table(name = "descriptive_characteristic_reference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DescriptiveCharacteristicReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    private String theBuildingMaterialArmenian;
    private String theBuildingMaterialEnglish;
    private String theBuildingMaterialFrench;
    private String typeArmenian;
    private String typeEnglish;
    private String typeFrench;

    private String colorArmenian;
    private String colorEnglish;
    private String colorFrench;

    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueArmenian;
    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueEnglish;
    @Column(columnDefinition = "TEXT")
    private String implementationTechniqueFrench;

    private String stateOfMonumentArmenian;
    private String stateOfMonumentEnglish;
    private String stateOfMonumentFrench;

    @Column(columnDefinition = "TEXT")
    private String valuationArmenian;
    @Column(columnDefinition = "TEXT")
    private String valuationEnglish;
    @Column(columnDefinition = "TEXT")
    private String valuationFrench;

    public DescriptiveCharacteristicReference(
        Monument monument,
        String theBuildingMaterialArmenian,
        String theBuildingMaterialEnglish,
        String theBuildingMaterialFrench,
        String typeArmenian,
        String typeEnglish,
        String typeFrench,
        String colorArmenian,
        String colorEnglish,
        String colorFrench,
        String implementationTechniqueArmenian,
        String implementationTechniqueEnglish,
        String implementationTechniqueFrench,
        String stateOfMonumentArmenian,
        String stateOfMonumentEnglish,
        String stateOfMonumentFrench,
        String valuationArmenian,
        String valuationEnglish,
        String valuationFrench)
    {
        this.monument = monument;
        this.theBuildingMaterialArmenian = theBuildingMaterialArmenian;
        this.theBuildingMaterialEnglish = theBuildingMaterialEnglish;
        this.theBuildingMaterialFrench = theBuildingMaterialFrench;
        this.typeArmenian = typeArmenian;
        this.typeEnglish = typeEnglish;
        this.typeFrench = typeFrench;
        this.colorArmenian = colorArmenian;
        this.colorEnglish = colorEnglish;
        this.colorFrench = colorFrench;
        this.implementationTechniqueArmenian = implementationTechniqueArmenian;
        this.implementationTechniqueEnglish = implementationTechniqueEnglish;
        this.implementationTechniqueFrench = implementationTechniqueFrench;
        this.stateOfMonumentArmenian = stateOfMonumentArmenian;
        this.stateOfMonumentEnglish = stateOfMonumentEnglish;
        this.stateOfMonumentFrench = stateOfMonumentFrench;
        this.valuationArmenian = valuationArmenian;
        this.valuationEnglish = valuationEnglish;
        this.valuationFrench = valuationFrench;
    }

    @Override
    public String toString() {
        return "DescriptiveCharacteristicReference{" +
            "id=" + id +
            ", monument=" + monument +
            ", theBuildingMaterialArmenian='" + theBuildingMaterialArmenian + '\'' +
            ", theBuildingMaterialEnglish='" + theBuildingMaterialEnglish + '\'' +
            ", theBuildingMaterialFrench='" + theBuildingMaterialFrench + '\'' +
            ", typeArmenian='" + typeArmenian + '\'' +
            ", typeEnglish='" + typeEnglish + '\'' +
            ", typeFrench='" + typeFrench + '\'' +
            ", colorArmenian='" + colorArmenian + '\'' +
            ", colorEnglish='" + colorEnglish + '\'' +
            ", colorFrench='" + colorFrench + '\'' +
            ", implementationTechniqueArmenian='" + implementationTechniqueArmenian + '\'' +
            ", implementationTechniqueEnglish='" + implementationTechniqueEnglish + '\'' +
            ", implementationTechniqueFrench='" + implementationTechniqueFrench + '\'' +
            ", stateOfMonumentArmenian='" + stateOfMonumentArmenian + '\'' +
            ", stateOfMonumentEnglish='" + stateOfMonumentEnglish + '\'' +
            ", stateOfMonumentFrench='" + stateOfMonumentFrench + '\'' +
            ", valuationArmenian='" + valuationArmenian + '\'' +
            ", valuationEnglish='" + valuationEnglish + '\'' +
            ", valuationFrench='" + valuationFrench + '\'' +
            '}';
    }
}