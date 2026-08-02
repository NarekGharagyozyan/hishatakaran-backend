package org.hishatakaran.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonumentTypeCountDto {

    private Long id;
    private LanguagesResponseDto name;
    private Long count;

    public MonumentTypeCountDto(
        Long id,
        String nameHy,
        String nameEn,
        String nameFr,
        Long count
    ) {
        this.id = id;
        this.name = LanguagesResponseDto.of(nameHy, nameEn, nameFr);
        this.count = count;
    }
}