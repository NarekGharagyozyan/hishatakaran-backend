package org.hishatakaran.backend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hishatakaran.backend.model.MonumentType;
import org.hishatakaran.backend.model.Status;


import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "monuments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Monument {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement", nullable = false)
    private Settlement settlement;

    @Enumerated(EnumType.STRING)
    @Column(name = "monument_type", nullable = false)
    private MonumentType monumentType;

    private String specialName;

    @ElementCollection
    @CollectionTable(name = "monument_another_names", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "another_name")
    private List<String> anotherNames = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String history;

    private String originalAffiliation;
    private String storageUnitName;

    @Column(name = "monument_condition")
    private String condition;

    @ElementCollection
    @CollectionTable(name = "monument_pictures", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "picture_url")
    private List<String> pictures = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bibliography> bibliography = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topographic> topographics = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricalReference> historicalReferences = new ArrayList<>();

    @OneToMany(mappedBy = "monument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DescriptiveCharacteristicReference> descriptiveCharacteristics = new ArrayList<>();
}