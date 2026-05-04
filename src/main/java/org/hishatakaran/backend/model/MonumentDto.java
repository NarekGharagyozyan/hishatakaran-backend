package org.hishatakaran.backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MonumentDto {

    public UUID id;
    public String name;
    public String status;
    public String monumentType;

    public String regionName;
    public Integer regionId;

    public String settlementName;
    public Integer settlementId;

    public String specialName;
    public List<String> anotherNames;

    public String history;
    public String originalAffiliation;
    public String storageUnitName;
    public String condition;

    public List<String> pictures;

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public List<BibliographyDto> bibliography;
    public List<TopographicDto> topographics;
    public List<HistoricalReferenceDto> historicalReferences;
    public List<DescriptiveCharacteristicDto> descriptiveCharacteristics;
}