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

    @Column(columnDefinition = "TEXT")
    private String culturalAffiliationHy;
    @Column(columnDefinition = "TEXT")
    private String culturalAffiliationEn;
    @Column(columnDefinition = "TEXT")
    private String culturalAffiliationFr;

    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnReliableDocumentHy;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnReliableDocumentEn;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnReliableDocumentFr;

    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyHy;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyEn;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnLithographyFr;

    @Column(columnDefinition = "TEXT", name = "justification_of_the_numbering_based_on_biblio_hy")
    private String justificationOfTheNumberingBasedOnBibliographicalSourcesHy;
    @Column(columnDefinition = "TEXT", name = "justification_of_the_numbering_based_on_biblio_en")
    private String justificationOfTheNumberingBasedOnBibliographicalSourcesEn;
    @Column(columnDefinition = "TEXT", name = "justification_of_the_numbering_based_on_biblio_fr")
    private String justificationOfTheNumberingBasedOnBibliographicalSourcesFr;

    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingAccordingIconographyHy;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingAccordingIconographyEn;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingAccordingIconographyFr;

    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnEvidenceHy;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnEvidenceEn;
    @Column(columnDefinition = "TEXT")
    private String justificationOfTheNumberingBasedOnEvidenceFr;

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

    @Column(columnDefinition = "TEXT")
    private String authorHy;
    @Column(columnDefinition = "TEXT")
    private String authorEn;
    @Column(columnDefinition = "TEXT")
    private String authorFr;

    @Column(columnDefinition = "TEXT")
    private String sourceForDeterminingTheAuthorHy;
    @Column(columnDefinition = "TEXT")
    private String sourceForDeterminingTheAuthorEn;
    @Column(columnDefinition = "TEXT")
    private String sourceForDeterminingTheAuthorFr;

    @Column(columnDefinition = "TEXT")
    private String briefHistoricalOverviewHy;
    @Column(columnDefinition = "TEXT")
    private String briefHistoricalOverviewEn;
    @Column(columnDefinition = "TEXT")
    private String briefHistoricalOverviewFr;

    public HistoricalReference(Monument monument, String culturalAffiliationHy, String culturalAffiliationEn,
        String culturalAffiliationFr, String justificationOfTheNumberingBasedOnReliableDocumentHy,
        String justificationOfTheNumberingBasedOnReliableDocumentEn,
        String justificationOfTheNumberingBasedOnReliableDocumentFr,
        String justificationOfTheNumberingBasedOnLithographyHy,
        String justificationOfTheNumberingBasedOnLithographyEn, String justificationOfTheNumberingBasedOnLithographyFr,
        String justificationOfTheNumberingBasedOnBibliographicalSourcesHy,
        String justificationOfTheNumberingBasedOnBibliographicalSourcesEn,
        String justificationOfTheNumberingBasedOnBibliographicalSourcesFr,
        String justificationOfTheNumberingAccordingIconographyHy,
        String justificationOfTheNumberingAccordingIconographyEn,
        String justificationOfTheNumberingAccordingIconographyFr, String justificationOfTheNumberingBasedOnEvidenceHy,
        String justificationOfTheNumberingBasedOnEvidenceEn, String justificationOfTheNumberingBasedOnEvidenceFr,
        String chronologicalTableOfTheStudHy, String chronologicalTableOfTheStudEn,
        String chronologicalTableOfTheStudFr,
        String chronologicalTableOfTheMonumentsStudyHy, String chronologicalTableOfTheMonumentsStudyEn,
        String chronologicalTableOfTheMonumentsStudyFr, String authorHy, String authorEn, String authorFr,
        String briefHistoricalOverviewHy, String briefHistoricalOverviewEn, String briefHistoricalOverviewFr) {
        this.monument = monument;
        this.culturalAffiliationHy = culturalAffiliationHy;
        this.culturalAffiliationEn = culturalAffiliationEn;
        this.culturalAffiliationFr = culturalAffiliationFr;
        this.justificationOfTheNumberingBasedOnReliableDocumentHy = justificationOfTheNumberingBasedOnReliableDocumentHy;
        this.justificationOfTheNumberingBasedOnReliableDocumentEn = justificationOfTheNumberingBasedOnReliableDocumentEn;
        this.justificationOfTheNumberingBasedOnReliableDocumentFr = justificationOfTheNumberingBasedOnReliableDocumentFr;
        this.justificationOfTheNumberingBasedOnLithographyHy = justificationOfTheNumberingBasedOnLithographyHy;
        this.justificationOfTheNumberingBasedOnLithographyEn = justificationOfTheNumberingBasedOnLithographyEn;
        this.justificationOfTheNumberingBasedOnLithographyFr = justificationOfTheNumberingBasedOnLithographyFr;
        this.justificationOfTheNumberingBasedOnBibliographicalSourcesHy = justificationOfTheNumberingBasedOnBibliographicalSourcesHy;
        this.justificationOfTheNumberingBasedOnBibliographicalSourcesEn = justificationOfTheNumberingBasedOnBibliographicalSourcesEn;
        this.justificationOfTheNumberingBasedOnBibliographicalSourcesFr = justificationOfTheNumberingBasedOnBibliographicalSourcesFr;
        this.justificationOfTheNumberingAccordingIconographyHy = justificationOfTheNumberingAccordingIconographyHy;
        this.justificationOfTheNumberingAccordingIconographyEn = justificationOfTheNumberingAccordingIconographyEn;
        this.justificationOfTheNumberingAccordingIconographyFr = justificationOfTheNumberingAccordingIconographyFr;
        this.justificationOfTheNumberingBasedOnEvidenceHy = justificationOfTheNumberingBasedOnEvidenceHy;
        this.justificationOfTheNumberingBasedOnEvidenceEn = justificationOfTheNumberingBasedOnEvidenceEn;
        this.justificationOfTheNumberingBasedOnEvidenceFr = justificationOfTheNumberingBasedOnEvidenceFr;
        this.chronologicalTableOfTheStudHy = chronologicalTableOfTheStudHy;
        this.chronologicalTableOfTheStudEn = chronologicalTableOfTheStudEn;
        this.chronologicalTableOfTheStudFr = chronologicalTableOfTheStudFr;
        this.chronologicalTableOfTheMonumentsStudyHy = chronologicalTableOfTheMonumentsStudyHy;
        this.chronologicalTableOfTheMonumentsStudyEn = chronologicalTableOfTheMonumentsStudyEn;
        this.chronologicalTableOfTheMonumentsStudyFr = chronologicalTableOfTheMonumentsStudyFr;
        this.authorHy = authorHy;
        this.authorEn = authorEn;
        this.authorFr = authorFr;
        this.briefHistoricalOverviewHy = briefHistoricalOverviewHy;
        this.briefHistoricalOverviewEn = briefHistoricalOverviewEn;
        this.briefHistoricalOverviewFr = briefHistoricalOverviewFr;
    }

    @Override
    public String toString() {
        return "HistoricalReference{" +
            "id=" + id +
            ", monument=" + monument +
            ", culturalAffiliationHy='" + culturalAffiliationHy + '\'' +
            ", culturalAffiliationEn='" + culturalAffiliationEn + '\'' +
            ", culturalAffiliationFr='" + culturalAffiliationFr + '\'' +
            ", justificationOfTheNumberingBasedOnReliableDocumentHy='" + justificationOfTheNumberingBasedOnReliableDocumentHy + '\'' +
            ", justificationOfTheNumberingBasedOnReliableDocumentEn='" + justificationOfTheNumberingBasedOnReliableDocumentEn + '\'' +
            ", justificationOfTheNumberingBasedOnReliableDocumentFr='" + justificationOfTheNumberingBasedOnReliableDocumentFr + '\'' +
            ", justificationOfTheNumberingBasedOnLithographyHy='" + justificationOfTheNumberingBasedOnLithographyHy + '\'' +
            ", justificationOfTheNumberingBasedOnLithographyEn='" + justificationOfTheNumberingBasedOnLithographyEn + '\'' +
            ", justificationOfTheNumberingBasedOnLithographyFr='" + justificationOfTheNumberingBasedOnLithographyFr + '\'' +
            ", justificationOfTheNumberingBasedOnBibliographicalSourcesHy='" + justificationOfTheNumberingBasedOnBibliographicalSourcesHy + '\'' +
            ", justificationOfTheNumberingBasedOnBibliographicalSourcesEn='" + justificationOfTheNumberingBasedOnBibliographicalSourcesEn + '\'' +
            ", justificationOfTheNumberingBasedOnBibliographicalSourcesFr='" + justificationOfTheNumberingBasedOnBibliographicalSourcesFr + '\'' +
            ", justificationOfTheNumberingAccordingIconographyHy='" + justificationOfTheNumberingAccordingIconographyHy + '\'' +
            ", justificationOfTheNumberingAccordingIconographyEn='" + justificationOfTheNumberingAccordingIconographyEn + '\'' +
            ", justificationOfTheNumberingAccordingIconographyFr='" + justificationOfTheNumberingAccordingIconographyFr + '\'' +
            ", justificationOfTheNumberingBasedOnEvidenceHy='" + justificationOfTheNumberingBasedOnEvidenceHy + '\'' +
            ", justificationOfTheNumberingBasedOnEvidenceEn='" + justificationOfTheNumberingBasedOnEvidenceEn + '\'' +
            ", justificationOfTheNumberingBasedOnEvidenceFr='" + justificationOfTheNumberingBasedOnEvidenceFr + '\'' +
            ", chronologicalTableOfTheStudHy='" + chronologicalTableOfTheStudHy + '\'' +
            ", chronologicalTableOfTheStudEn='" + chronologicalTableOfTheStudEn + '\'' +
            ", chronologicalTableOfTheStudFr='" + chronologicalTableOfTheStudFr + '\'' +
            ", chronologicalTableOfTheMonumentsStudyHy='" + chronologicalTableOfTheMonumentsStudyHy + '\'' +
            ", chronologicalTableOfTheMonumentsStudyEn='" + chronologicalTableOfTheMonumentsStudyEn + '\'' +
            ", chronologicalTableOfTheMonumentsStudyFr='" + chronologicalTableOfTheMonumentsStudyFr + '\'' +
            ", authorHy='" + authorHy + '\'' +
            ", authorEn='" + authorEn + '\'' +
            ", authorFr='" + authorFr + '\'' +
            ", briefHistoricalOverviewHy='" + briefHistoricalOverviewHy + '\'' +
            ", briefHistoricalOverviewEn='" + briefHistoricalOverviewEn + '\'' +
            ", briefHistoricalOverviewFr='" + briefHistoricalOverviewFr + '\'' +
            '}';
    }
}