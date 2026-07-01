package org.hishatakaran.backend.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
@Table(name = "library")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleHy;
    private String titleEn;
    private String titleFr;

    private String descriptionHy;
    private String descriptionEn;
    private String descriptionFr;

    private String copyrightTextHy;
    private String copyrightTextEn;
    private String copyrightTextFr;
    private String copyrightUrl;

    private String bookUrl;
    private String coverUrl;

    private String authorsHy;
    private String authorsEn;
    private String authorsFr;

    public Library(String titleHy, String titleEn, String titleFr, String descriptionHy, String descriptionEn,
        String descriptionFr, String copyrightTextHy, String copyrightTextEn, String copyrightTextFr,
        String copyrightUrl,
        String bookUrl, String coverUrl, String authorsHy, String authorsEn, String authorsFr) {
        this.titleHy = titleHy;
        this.titleEn = titleEn;
        this.titleFr = titleFr;
        this.descriptionHy = descriptionHy;
        this.descriptionEn = descriptionEn;
        this.descriptionFr = descriptionFr;
        this.copyrightTextHy = copyrightTextHy;
        this.copyrightTextEn = copyrightTextEn;
        this.copyrightTextFr = copyrightTextFr;
        this.copyrightUrl = copyrightUrl;
        this.bookUrl = bookUrl;
        this.coverUrl = coverUrl;
        this.authorsHy = authorsHy;
        this.authorsEn = authorsEn;
        this.authorsFr = authorsFr;
    }
}