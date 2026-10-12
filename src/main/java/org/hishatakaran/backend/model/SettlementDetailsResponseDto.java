package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SettlementDetailsResponseDto {

    private Long id;
    private Long regionId;
    private LanguagesResponseDto name;
    private SettlementTypeResponseDto settlementType;
    private LanguagesResponseDto anotherNames;
    private LanguagesResponseDto history;
    private LanguagesResponseDto description;
    private LanguagesResponseDto dataUpdate;
    private SettlementTopographicResponseDto topographics;
    private List<BibliographyResponseDto> bibliography;
    private List<ImageResponseDto> images;
    private List<MeasurementResponseDto> measurements;
    private List<MonumentVideoResponseDto> videos;
}
