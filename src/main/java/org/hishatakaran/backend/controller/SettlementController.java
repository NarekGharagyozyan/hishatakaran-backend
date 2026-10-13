package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.model.SettlementDetailsResponseDto;
import org.hishatakaran.backend.model.SettlementEditDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementTypeResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
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
    public List<SettlementDetailsResponseDto> getByRegion(@PathVariable Long regionId) {
        return settlementService.getByRegion(regionId);
    }

    @GetMapping("/settlements")
    public List<SettlementDetailsResponseDto> getAll() {
        return settlementService.getAll();
    }

    @GetMapping("/settlements/types")
    public List<SettlementTypeResponseDto> getAllSettlementTypes() {
        return settlementService.getAllSettlementTypes();
    }

    @PostMapping("/admin/region/{regionId}/settlements")
    public SettlementDetailsResponseDto addNewSettlement(
        @PathVariable Long regionId,
        @RequestBody SettlementRequestDto settlementRequestDto
    ) {
        return settlementService.createNewSettlement(regionId, settlementRequestDto);
    }

    @PutMapping("/admin/settlements/{settlementId}")
    public SettlementDetailsResponseDto editSettlement(
        @PathVariable Long settlementId,
        @RequestBody SettlementEditDto settlementEditDto
    ) {
        return settlementService.editSettlement(settlementId, settlementEditDto);
    }

    @PostMapping("/admin/settlements/{settlementId}/translate/{language}")
    public SettlementDetailsResponseDto translate(
        @PathVariable Long settlementId,
        @PathVariable TranslationLanguage language
    ) {
        return settlementService.translate(settlementId, language);
    }

    @DeleteMapping("/admin/settlements/{settlementId}")
    public void deleteSettlement(@PathVariable Long settlementId) {
        settlementService.deleteSettlement(settlementId);
    }

    @GetMapping("/settlements/{settlementId}")
    public SettlementDetailsResponseDto getSettlement(@PathVariable Long settlementId) {
        return settlementService.getSettlement(settlementId);
    }
}
