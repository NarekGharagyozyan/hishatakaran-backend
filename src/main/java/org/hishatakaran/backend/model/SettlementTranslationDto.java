package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/** One language per call, like {@link MonumentTranslationDto}. Coordinates are not translated. */
@Getter
@Setter
public class SettlementTranslationDto {

    private String name;

    private String anotherNames;

    private String history;

    private String description;

    private String dataUpdate;

    private SettlementTopographicTranslationDto topographics;

    private List<BibliographyTranslationDto> bibliography;

    private List<ImageAndMeasurementTranslationDto> images;

    private List<ImageAndMeasurementTranslationDto> measurements;

    private List<VideoTranslationDto> videos;
}
