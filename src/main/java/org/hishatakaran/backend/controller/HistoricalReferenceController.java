package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.HistoricalReferenceMapper;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;
import org.hishatakaran.backend.repository.HistoricalReferenceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public HistoricalReferenceResponseDto getById(@PathVariable Long id) {
        return HistoricalReferenceMapper.toDto(
            historicalReferenceRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/monument/{monumentId}")
    public List<HistoricalReferenceResponseDto> getByMonument(@PathVariable Long monumentId) {
        return historicalReferenceRepository.findByMonumentId(monumentId)
            .stream()
            .map(HistoricalReferenceMapper::toDto)
            .toList();
    }
}