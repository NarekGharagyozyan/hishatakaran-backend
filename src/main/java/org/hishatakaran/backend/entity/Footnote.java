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
@Table(name = "footnotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Footnote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderNumber;

    private String textHy;
    private String textEn;
    private String textFr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monument_id")
    private Monument monument;

    public Footnote(Monument monument, Integer orderNumber, String textHy, String textEn, String textFr) {
        this.monument = monument;
        this.orderNumber = orderNumber;
        this.textHy = textHy;
        this.textEn = textEn;
        this.textFr = textFr;
    }
}