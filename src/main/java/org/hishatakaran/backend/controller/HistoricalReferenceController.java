package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.HistoricalReferenceMapper;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;
import org.hishatakaran.backend.repository.HistoricalReferenceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/historical-references")
@RequiredArgsConstructor
public class HistoricalReferenceController {

    private final HistoricalReferenceRepository historicalReferenceRepository;

    @GetMapping
    public List<HistoricalReferenceResponseDto> getAll() {
        return historicalReferenceRepository.findAll()
            .stream()
            .map(HistoricalReferenceMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public HistoricalReferenceResponseDto getById(@PathVariable UUID id) {
        return HistoricalReferenceMapper.toDto(
            historicalReferenceRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/monument/{monumentId}")
    public List<HistoricalReferenceResponseDto> getByMonument(@PathVariable UUID monumentId) {
        return historicalReferenceRepository.findByMonumentId(monumentId)
            .stream()
            .map(HistoricalReferenceMapper::toDto)
            .toList();
    }
}