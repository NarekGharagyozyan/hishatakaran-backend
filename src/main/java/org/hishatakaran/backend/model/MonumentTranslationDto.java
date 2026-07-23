package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MonumentTranslationDto {

    private String name;

    private String specialName;

    private String anotherNames;

    private String history;

    private String originalAffiliation;

    private String storageUnitName;

    private String individuallyCertifiablePartsOfTheStorageUnit;

    private List<FootnoteTranslationDto> footnotes;

    private TopographicTranslationDto topographics;

    private HistoricalReferenceTranslationDto historicalReferences;

    private DescriptiveCharacteristicTranslationDto descriptiveCharacteristics;

    private List<BibliographyTranslationDto> bibliography;

    private List<VideoTranslationDto> videos;

    private List<ImageAndMeasurementTranslationDto> images;

    private List<ImageAndMeasurementTranslationDto> measurements;
}