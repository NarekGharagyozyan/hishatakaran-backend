package org.hishatakaran.backend.entity;

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

    private String provinceHy;
    private String provinceEn;
    private String provinceFr;

    private String addressHy;
    private String addressEn;
    private String addressFr;

    @Column(columnDefinition = "TEXT")
    private String topographyHy;
    @Column(columnDefinition = "TEXT")
    private String topographyEn;
    @Column(columnDefinition = "TEXT")
    private String topographyFr;

    private String distanceFromResidenceHy;
    private String distanceFromResidenceEn;
    private String distanceFromResidenceFr;
    private String longitude;
    private String latitude;
    private Integer altitude;

    @Column(columnDefinition = "TEXT")
    private String hydrographyHy;
    @Column(columnDefinition = "TEXT")
    private String hydrographyEn;
    @Column(columnDefinition = "TEXT")
    private String hydrographyFr;

    @Column(columnDefinition = "TEXT")
    private String descriptionHy;
    @Column(columnDefinition = "TEXT")
    private String descriptionEn;
    @Column(columnDefinition = "TEXT")
    private String descriptionFr;

    public Topographic(
        Monument monument,
        String provinceHy,
        String provinceEn,
        String provinceFr,
        String addressHy,
        String addressEn,
        String addressFr,
        String topographyHy,
        String topographyEn,
        String topographyFr,
        String distanceFromResidenceHy,
        String distanceFromResidenceEn,
        String distanceFromResidenceFr,
        String latitude,
        String longitude,
        Integer altitude,
        String hydrographyHy,
        String hydrographyEn,
        String hydrographyFr,
        String descriptionHy,
        String descriptionEn,
        String descriptionFr
    )
    {
        this.monument = monument;
        this.provinceHy = provinceHy;
        this.provinceEn = provinceEn;
        this.provinceFr = provinceFr;
        this.addressHy = addressHy;
        this.addressEn = addressEn;
        this.addressFr = addressFr;
        this.topographyHy = topographyHy;
        this.topographyEn = topographyEn;
        this.topographyFr = topographyFr;
        this.distanceFromResidenceHy = distanceFromResidenceHy;
        this.distanceFromResidenceEn = distanceFromResidenceEn;
        this.distanceFromResidenceFr = distanceFromResidenceFr;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.hydrographyHy = hydrographyHy;
        this.hydrographyEn = hydrographyEn;
        this.hydrographyFr = hydrographyFr;
        this.descriptionHy = descriptionHy;
        this.descriptionEn = descriptionEn;
        this.descriptionFr = descriptionFr;
    }

    @Override
    public String toString() {
        return "Topographic{" +
            "id=" + id +
            ", monument=" + monument +
            ", provinceHy='" + provinceHy + '\'' +
            ", provinceEn='" + provinceEn + '\'' +
            ", provinceFr='" + provinceFr + '\'' +
            ", addressHy='" + addressHy + '\'' +
            ", addressEn='" + addressEn + '\'' +
            ", addressFr='" + addressFr + '\'' +
            ", topographyHy='" + topographyHy + '\'' +
            ", topographyEn='" + topographyEn + '\'' +
            ", topographyFr='" + topographyFr + '\'' +
            ", distanceFromResidenceHy='" + distanceFromResidenceHy + '\'' +
            ", distanceFromResidenceEn='" + distanceFromResidenceEn + '\'' +
            ", distanceFromResidenceFr='" + distanceFromResidenceFr + '\'' +
            ", longitude='" + longitude + '\'' +
            ", latitude='" + latitude + '\'' +
            ", altitude=" + altitude +
            ", hydrographyHy='" + hydrographyHy + '\'' +
            ", hydrographyEn='" + hydrographyEn + '\'' +
            ", hydrographyFr='" + hydrographyFr + '\'' +
            ", descriptionHy='" + descriptionHy + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", descriptionFr='" + descriptionFr + '\'' +
            '}';
    }
}