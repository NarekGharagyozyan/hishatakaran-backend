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
@Table(name = "historicalReferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricalReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    private String culturalAffiliationHy;
    private String culturalAffiliationEn;
    private String culturalAffiliationFr;
    private String centuryHy;
    private String centuryEn;
    private String centuryFr;

    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyHy;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyEn;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyFr;

    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheStudHy;
    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheStudEn;
    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheStudFr;

    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheMonumentsStudyHy;
    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheMonumentsStudyEn;
    @Column(columnDefinition = "TEXT")
    private String chronologicalTableOfTheMonumentsStudyFr;

    private String authorHy;
    private String authorEn;
    private String authorFr;

    public HistoricalReference(Monument monument, String culturalAffiliationHy, String culturalAffiliationEn,
        String culturalAffiliationFr, String centuryHy, String centuryEn, String centuryFr,
        String justificationOfTheNumberingBasedOnLithographyHy, String justificationOfTheNumberingBasedOnLithographyEn,
        String justificationOfTheNumberingBasedOnLithographyFr, String chronologicalTableOfTheStudHy,
        String chronologicalTableOfTheStudEn, String chronologicalTableOfTheStudFr,
        String chronologicalTableOfTheMonumentsStudyHy, String chronologicalTableOfTheMonumentsStudyEn,
        String chronologicalTableOfTheMonumentsStudyFr, String authorHy, String authorEn, String authorFr) {
        this.monument = monument;
        this.culturalAffiliationHy = culturalAffiliationHy;
        this.culturalAffiliationEn = culturalAffiliationEn;
        this.culturalAffiliationFr = culturalAffiliationFr;
        this.centuryHy = centuryHy;
        this.centuryEn = centuryEn;
        this.centuryFr = centuryFr;
        this.justificationOfTheNumberingBasedOnLithographyHy = justificationOfTheNumberingBasedOnLithographyHy;
        this.justificationOfTheNumberingBasedOnLithographyEn = justificationOfTheNumberingBasedOnLithographyEn;
        this.justificationOfTheNumberingBasedOnLithographyFr = justificationOfTheNumberingBasedOnLithographyFr;
        this.chronologicalTableOfTheStudHy = chronologicalTableOfTheStudHy;
        this.chronologicalTableOfTheStudEn = chronologicalTableOfTheStudEn;
        this.chronologicalTableOfTheStudFr = chronologicalTableOfTheStudFr;
        this.chronologicalTableOfTheMonumentsStudyHy = chronologicalTableOfTheMonumentsStudyHy;
        this.chronologicalTableOfTheMonumentsStudyEn = chronologicalTableOfTheMonumentsStudyEn;
        this.chronologicalTableOfTheMonumentsStudyFr = chronologicalTableOfTheMonumentsStudyFr;
        this.authorHy = authorHy;
        this.authorEn = authorEn;
        this.authorFr = authorFr;
    }

    @Override
    public String toString() {
        return "HistoricalReference{" +
            "id=" + id +
            ", culturalAffiliationHy='" + culturalAffiliationHy + '\'' +
            ", culturalAffiliationEn='" + culturalAffiliationEn + '\'' +
            ", culturalAffiliationFr='" + culturalAffiliationFr + '\'' +
            ", centuryHy='" + centuryHy + '\'' +
            ", centuryEn='" + centuryEn + '\'' +
            ", centuryFr='" + centuryFr + '\'' +
            ", justificationOfTheNumberingBasedOnLithographyHy='" + justificationOfTheNumberingBasedOnLithographyHy + '\'' +
            ", justificationOfTheNumberingBasedOnLithographyEn='" + justificationOfTheNumberingBasedOnLithographyEn + '\'' +
            ", justificationOfTheNumberingBasedOnLithographyFr='" + justificationOfTheNumberingBasedOnLithographyFr + '\'' +
            ", chronologicalTableOfTheStudHy='" + chronologicalTableOfTheStudHy + '\'' +
            ", chronologicalTableOfTheStudEn='" + chronologicalTableOfTheStudEn + '\'' +
            ", chronologicalTableOfTheStudFr='" + chronologicalTableOfTheStudFr + '\'' +
            ", chronologicalTableOfTheMonumentsStudyHy='" + chronologicalTableOfTheMonumentsStudyHy + '\'' +
            ", chronologicalTableOfTheMonumentsStudyEn='" + chronologicalTableOfTheMonumentsStudyEn + '\'' +
            ", chronologicalTableOfTheMonumentsStudyFr='" + chronologicalTableOfTheMonumentsStudyFr + '\'' +
            ", authorHy='" + authorHy + '\'' +
            ", authorEn='" + authorEn + '\'' +
            ", authorFr='" + authorFr + '\'' +
            '}';
    }
}