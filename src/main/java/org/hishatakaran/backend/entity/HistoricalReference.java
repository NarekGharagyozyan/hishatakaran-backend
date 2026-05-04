package org.hishatakaran.backend.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "historicalReferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalReference {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    private String culturalAffiliation;
    private String century;

    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithography;

    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheStud;

    private String author;
}