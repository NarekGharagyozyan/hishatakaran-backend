package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonumentFilterRequest {

    private Long regionId;
    private Long settlementId;
    private String monumentType;
}