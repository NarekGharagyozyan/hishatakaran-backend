package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.MonumentDto;
import org.hishatakaran.backend.model.MonumentType;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/monuments")
@RequiredArgsConstructor
public class MonumentController {

    private final MonumentRepository monumentRepository;

    @GetMapping
    public List<MonumentDto> getAll() {
        return monumentRepository.findAll()
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public Monument getById(@PathVariable UUID id) {
        return monumentRepository.findById(id).orElseThrow();
    }

    @GetMapping("/status/{status}")
    public List<Monument> getByStatus(@PathVariable Status status) {
        return monumentRepository.findByStatus(status);
    }

    @GetMapping("/region/{regionId}")
    public List<Monument> getByRegion(@PathVariable Integer regionId) {
        return monumentRepository.findByRegionId(regionId);
    }

    @GetMapping("/settlement/{settlementId}")
    public List<Monument> getBySettlement(@PathVariable Integer settlementId) {
        return monumentRepository.findBySettlementId(settlementId);
    }

    @GetMapping("/type/{type}")
    public List<Monument> getByType(@PathVariable MonumentType type) {
        return monumentRepository.findByMonumentType(type);
    }

    @GetMapping("/search")
    public List<Monument> search(@RequestParam String q) {
        return monumentRepository.findByNameContainingIgnoreCase(q);
    }
}