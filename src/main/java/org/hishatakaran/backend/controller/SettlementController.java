package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.model.SettlementEditDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.service.SettlementService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/region/{regionId}/settlements")
    @Query("select s from settlements s where s.region = ?1")
    public List<SettlementResponseDto> getByRegion(@PathVariable Long regionId) {
        return settlementService.getByRegion(regionId);
    }

    @GetMapping("/settlements")
    public List<SettlementResponseDto> getAll() {
        return settlementService.getAll();
    }

    @PostMapping("/admin/region/{regionId}/settlements")
    public SettlementResponseDto addNewSettlement(
        @PathVariable Long regionId,
        @RequestBody SettlementRequestDto settlementRequestDto
    ) {
        return settlementService.createNewSettlement(regionId, settlementRequestDto);
    }

    @PutMapping("/admin/settlements/{settlementId}")
    public SettlementResponseDto editSettlement(
        @PathVariable Long settlementId,
        @RequestBody SettlementEditDto settlementEditDto
    ) {
        return settlementService.editSettlement(settlementId, settlementEditDto);
    }

    @DeleteMapping("/admin/settlements/{settlementId}")
    public void deleteSettlement(@PathVariable Long settlementId) {
        settlementService.deleteSettlement(settlementId);
    }

    @GetMapping("/settlements/{settlementId}")
    public SettlementResponseDto getSettlement(@PathVariable Long settlementId) {
        return settlementService.getSettlement(settlementId);
    }
}