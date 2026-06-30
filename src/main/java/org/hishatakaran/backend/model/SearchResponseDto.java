package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchResponseDto {

    private List<MonumentResponseDto> monuments;
    private List<RegionResponseDto> regions;
    private List<SettlementResponseDto> settlements;
    private List<NewsResponseDto> news;
    private List<LibraryResponseDto> libraries;
}