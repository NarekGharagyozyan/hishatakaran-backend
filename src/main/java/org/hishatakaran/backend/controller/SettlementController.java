package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementRepository settlementRepository;

    @GetMapping
    public List<Settlement> getAll() {
        return settlementRepository.findAll();
    }

    @GetMapping("/{id}")
    public Settlement getById(@PathVariable Integer id) {
        return settlementRepository.findById(id).orElseThrow();
    }

    @GetMapping("/region/{regionId}")
    public List<Settlement> getByRegion(@PathVariable Integer regionId) {
        return settlementRepository.findByRegionId(regionId);
    }
}