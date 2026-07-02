package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.SettlementMapper;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementRepository settlementRepository;

    @GetMapping("/region/{regionId}/settlements")
    @Query("select s from settlements s where s.region = ?1")
    public List<SettlementResponseDto> getByRegion(@PathVariable Long regionId) {
        return settlementRepository.findAllByRegionId(regionId)
            .stream()
            .map(SettlementMapper::toDto)
            .toList();
    }
}