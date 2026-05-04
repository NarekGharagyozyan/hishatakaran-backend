package org.hishatakaran.backend.entity;

import java.util.UUID;

import org.hishatakaran.backend.model.Color;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    private String theBuildingMaterial;
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @Column(columnDefinition = "TEXT")
    private String implementationTechnique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateOfMonument", nullable = false)
    private StateOfMonument stateOfMonument;

    @Column(columnDefinition = "TEXT")
    private String valuation;
}