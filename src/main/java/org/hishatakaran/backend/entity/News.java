package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hishatakaran.backend.model.Status;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleArmenian;

    private String titleEnglish;

    private String titleFrench;

    @Column(columnDefinition = "TEXT")
    private String textArmenian;

    @Column(columnDefinition = "TEXT")
    private String textEnglish;

    @Column(columnDefinition = "TEXT")
    private String textFrench;

    @ElementCollection
    @CollectionTable(name = "news_pictures", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "picture_url")
    private List<String> pictures = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}