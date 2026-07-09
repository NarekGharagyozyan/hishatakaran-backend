package org.hishatakaran.backend.entity;


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
@Table(name = "bibliography")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bibliography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument", nullable = false)
    private Monument monument;

    private String titleHy;
    private String titleEn;
    private String titleFr;
    private String url;

    public Bibliography(Monument monument, String titleHy, String titleEn, String titleFr, String url) {
        this.monument = monument;
        this.titleHy = titleHy;
        this.titleEn = titleEn;
        this.titleFr = titleFr;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Bibliography{" +
            "id=" + id +
            ", titleHy='" + titleHy + '\'' +
            ", titleEn='" + titleEn + '\'' +
            ", titleFr='" + titleFr + '\'' +
            ", url='" + url + '\'' +
            '}';
    }
}