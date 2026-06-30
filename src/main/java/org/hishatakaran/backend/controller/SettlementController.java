package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.SettlementMapper;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementRepository settlementRepository;

    @GetMapping
    public List<SettlementResponseDto> getAll() {
        return settlementRepository.findAll()
            .stream()
            .map(SettlementMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public SettlementResponseDto getById(@PathVariable Long id) {
        return SettlementMapper.toDto(
            settlementRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/region/{regionId}")
    public List<SettlementResponseDto> getByRegion(@PathVariable Long regionId) {
        return settlementRepository.findByRegionId(regionId)
            .stream()
            .map(SettlementMapper::toDto)
            .toList();
    }
}