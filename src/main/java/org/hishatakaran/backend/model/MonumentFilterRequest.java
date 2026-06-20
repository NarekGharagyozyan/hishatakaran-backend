package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonumentFilterRequest {

    private Integer regionId;
    private Integer settlementId;
    private MonumentType monumentType;
}