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
@Table(name = "topographics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topographic {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    private String regionHistory;
    private String address;

    @Column(columnDefinition = "TEXT")
    private String topography;

    private String distanceFromResidence;
    private Integer altitude;

    @Column(columnDefinition = "TEXT")
    private String hydrography;

    @Column(columnDefinition = "TEXT")
    private String description;
}