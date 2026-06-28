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
@Table(name = "topographics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topographic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    private String provinceArmenian;
    private String provinceEnglish;
    private String provinceFrench;

    private String addressArmenian;
    private String addressEnglish;
    private String addressFrench;

    @Column(columnDefinition = "TEXT")
    private String topographyArmenian;
    @Column(columnDefinition = "TEXT")
    private String topographyEnglish;
    @Column(columnDefinition = "TEXT")
    private String topographyFrench;

    private String distanceFromResidenceArmenian;
    private String distanceFromResidenceEnglish;
    private String distanceFromResidenceFrench;
    private String longitude;
    private String latitude;
    private Integer altitude;

    @Column(columnDefinition = "TEXT")
    private String hydrographyArmenian;
    @Column(columnDefinition = "TEXT")
    private String hydrographyEnglish;
    @Column(columnDefinition = "TEXT")
    private String hydrographyFrench;

    @Column(columnDefinition = "TEXT")
    private String descriptionArmenian;
    @Column(columnDefinition = "TEXT")
    private String descriptionEnglish;
    @Column(columnDefinition = "TEXT")
    private String descriptionFrench;

    public Topographic(
        Monument monument,
        String provinceArmenian,
        String provinceEnglish,
        String provinceFrench,
        String addressArmenian,
        String addressEnglish,
        String addressFrench,
        String topographyArmenian,
        String topographyEnglish,
        String topographyFrench,
        String distanceFromResidenceArmenian,
        String distanceFromResidenceEnglish,
        String distanceFromResidenceFrench,
        String latitude,
        String longitude,
        Integer altitude,
        String hydrographyArmenian,
        String hydrographyEnglish,
        String hydrographyFrench,
        String descriptionArmenian,
        String descriptionEnglish,
        String descriptionFrench)
    {
        this.monument = monument;
        this.provinceArmenian = provinceArmenian;
        this.provinceEnglish = provinceEnglish;
        this.provinceFrench = provinceFrench;
        this.addressArmenian = addressArmenian;
        this.addressEnglish = addressEnglish;
        this.addressFrench = addressFrench;
        this.topographyArmenian = topographyArmenian;
        this.topographyEnglish = topographyEnglish;
        this.topographyFrench = topographyFrench;
        this.distanceFromResidenceArmenian = distanceFromResidenceArmenian;
        this.distanceFromResidenceEnglish = distanceFromResidenceEnglish;
        this.distanceFromResidenceFrench = distanceFromResidenceFrench;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.hydrographyArmenian = hydrographyArmenian;
        this.hydrographyEnglish = hydrographyEnglish;
        this.hydrographyFrench = hydrographyFrench;
        this.descriptionArmenian = descriptionArmenian;
        this.descriptionEnglish = descriptionEnglish;
        this.descriptionFrench = descriptionFrench;
    }

    @Override
    public String toString() {
        return "Topographic{" +
            "id=" + id +
            ", monument=" + monument +
            ", provinceArmenian='" + provinceArmenian + '\'' +
            ", provinceEnglish='" + provinceEnglish + '\'' +
            ", provinceFrench='" + provinceFrench + '\'' +
            ", addressArmenian='" + addressArmenian + '\'' +
            ", addressEnglish='" + addressEnglish + '\'' +
            ", addressFrench='" + addressFrench + '\'' +
            ", topographyArmenian='" + topographyArmenian + '\'' +
            ", topographyEnglish='" + topographyEnglish + '\'' +
            ", topographyFrench='" + topographyFrench + '\'' +
            ", distanceFromResidenceArmenian='" + distanceFromResidenceArmenian + '\'' +
            ", distanceFromResidenceEnglish='" + distanceFromResidenceEnglish + '\'' +
            ", distanceFromResidenceFrench='" + distanceFromResidenceFrench + '\'' +
            ", longitude='" + longitude + '\'' +
            ", latitude='" + latitude + '\'' +
            ", altitude=" + altitude +
            ", hydrographyArmenian='" + hydrographyArmenian + '\'' +
            ", hydrographyEnglish='" + hydrographyEnglish + '\'' +
            ", hydrographyFrench='" + hydrographyFrench + '\'' +
            ", descriptionArmenian='" + descriptionArmenian + '\'' +
            ", descriptionEnglish='" + descriptionEnglish + '\'' +
            ", descriptionFrench='" + descriptionFrench + '\'' +
            '}';
    }
}