package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.HistoricalReference;
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
    public List<HistoricalReference> getAll() {
        return historicalReferenceRepository.findAll();
    }

    @GetMapping("/{id}")
    public HistoricalReference getById(@PathVariable UUID id) {
        return historicalReferenceRepository.findById(id).orElseThrow();
    }

    @GetMapping("/monument/{monumentId}")
    public List<HistoricalReference> getByMonument(@PathVariable UUID monumentId) {
        return historicalReferenceRepository.findByMonumentId(monumentId);
    }
}