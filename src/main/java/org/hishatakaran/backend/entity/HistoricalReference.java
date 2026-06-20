package org.hishatakaran.backend.entity;

import java.util.UUID;


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

    private String culturalAffiliationArmenian;
    private String culturalAffiliationEnglish;
    private String culturalAffiliationFrench;
    private String centuryArmenian;
    private String centuryEnglish;
    private String centuryFrench;

    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyArmenian;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyEnglish;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyFrench;

    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheStudArmenian;
    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheStudEnglish;
    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheStudFrench;

    private String authorArmenian;
    private String authorEnglish;
    private String authorFrench;

    public HistoricalReference(
        Monument monument,
        String culturalAffiliationArmenian,
        String culturalAffiliationEnglish,
        String culturalAffiliationFrench,
        String centuryArmenian,
        String centuryEnglish,
        String centuryFrench,
        String justificationOfTheNumberingBasedOnLithographyArmenian,
        String justificationOfTheNumberingBasedOnLithographyEnglish,
        String justificationOfTheNumberingBasedOnLithographyFrench,
        String chronologicalTableOfTheStudArmenian,
        String chronologicalTableOfTheStudEnglish,
        String chronologicalTableOfTheStudFrench,
        String authorArmenian,
        String authorEnglish,
        String authorFrench)
    {
        this.monument = monument;
        this.culturalAffiliationArmenian = culturalAffiliationArmenian;
        this.culturalAffiliationEnglish = culturalAffiliationEnglish;
        this.culturalAffiliationFrench = culturalAffiliationFrench;
        this.centuryArmenian = centuryArmenian;
        this.centuryEnglish = centuryEnglish;
        this.centuryFrench = centuryFrench;
        this.justificationOfTheNumberingBasedOnLithographyArmenian = justificationOfTheNumberingBasedOnLithographyArmenian;
        this.justificationOfTheNumberingBasedOnLithographyEnglish = justificationOfTheNumberingBasedOnLithographyEnglish;
        this.justificationOfTheNumberingBasedOnLithographyFrench = justificationOfTheNumberingBasedOnLithographyFrench;
        this.chronologicalTableOfTheStudArmenian = chronologicalTableOfTheStudArmenian;
        this.chronologicalTableOfTheStudEnglish = chronologicalTableOfTheStudEnglish;
        this.chronologicalTableOfTheStudFrench = chronologicalTableOfTheStudFrench;
        this.authorArmenian = authorArmenian;
        this.authorEnglish = authorEnglish;
        this.authorFrench = authorFrench;
    }
}