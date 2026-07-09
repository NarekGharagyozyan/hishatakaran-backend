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
@Table(name = "topographics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topographic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    @Column(columnDefinition = "TEXT")
    private String regionHy;
    @Column(columnDefinition = "TEXT")
    private String regionEn;
    @Column(columnDefinition = "TEXT")
    private String regionFr;

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

    private String altitudeHy;
    private String altitudeEn;
    private String altitudeFr;

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
        String regionHy,
        String regionEn,
        String regionFr,
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
        String altitudeHy,
        String altitudeEn,
        String altitudeFr,
        String hydrographyHy,
        String hydrographyEn,
        String hydrographyFr,
        String descriptionHy,
        String descriptionEn,
        String descriptionFr
    )
    {
        this.monument = monument;
        this.regionHy = regionHy;
        this.regionEn = regionEn;
        this.regionFr = regionFr;
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
        this.altitudeHy = altitudeHy;
        this.altitudeEn = altitudeEn;
        this.altitudeFr = altitudeFr;
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
            ", regionHy='" + regionHy + '\'' +
            ", regionEn='" + regionEn + '\'' +
            ", regionFr='" + regionFr + '\'' +
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
            ", altitudeHy=" + altitudeHy +
            ", altitudeEn=" + altitudeEn +
            ", altitudeFr=" + altitudeFr +
            ", hydrographyHy='" + hydrographyHy + '\'' +
            ", hydrographyEn='" + hydrographyEn + '\'' +
            ", hydrographyFr='" + hydrographyFr + '\'' +
            ", descriptionHy='" + descriptionHy + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", descriptionFr='" + descriptionFr + '\'' +
            '}';
    }
}