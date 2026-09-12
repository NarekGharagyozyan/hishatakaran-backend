package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;
import org.hishatakaran.backend.service.HistoricalReferenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historical-references")
@RequiredArgsConstructor
public class HistoricalReferenceController {

    private final HistoricalReferenceService historicalReferenceService;

    @GetMapping
    public List<HistoricalReferenceResponseDto> getAll() {
        return historicalReferenceService.getAll();
    }

    @GetMapping("/{id}")
    public HistoricalReferenceResponseDto getById(@PathVariable Long id) {
        return historicalReferenceService.getById(id);
    }

//    @GetMapping("/monument/{monumentId}")
//    public List<HistoricalReferenceResponseDto> getByMonument(@PathVariable Long monumentId) {
//        return historicalReferenceService.getByMonument(monumentId);
//    }
}
