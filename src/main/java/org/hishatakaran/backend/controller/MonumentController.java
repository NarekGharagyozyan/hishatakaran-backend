package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.MonumentResponseDto;
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
    public List<MonumentResponseDto> getAll() {
        return monumentRepository.findAll()
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public MonumentResponseDto getById(@PathVariable UUID id) {
        return MonumentMapper.toDto(
            monumentRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/status/{status}")
    public List<MonumentResponseDto> getByStatus(@PathVariable Status status) {
        return monumentRepository.findByStatus(status)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/region/{regionId}")
    public List<MonumentResponseDto> getByRegion(@PathVariable Integer regionId) {
        return monumentRepository.findByRegionId(regionId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/settlement/{settlementId}")
    public List<MonumentResponseDto> getBySettlement(@PathVariable Integer settlementId) {
        return monumentRepository.findBySettlementId(settlementId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/type/{type}")
    public List<MonumentResponseDto> getByType(@PathVariable MonumentType type) {
        return monumentRepository.findByMonumentType(type)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/search")
    public List<MonumentResponseDto> search(@RequestParam String q) {
        return monumentRepository.findByNameContainingIgnoreCase(q)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }
}