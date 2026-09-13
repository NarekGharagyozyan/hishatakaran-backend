package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.model.TopographicResponseDto;
import org.hishatakaran.backend.service.TopographicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topographics")
@RequiredArgsConstructor
public class TopographicController {

    private final TopographicService topographicService;

    @GetMapping
    public List<TopographicResponseDto> getAll() {
        return topographicService.getAll();
    }

    @GetMapping("/{id}")
    public TopographicResponseDto getById(@PathVariable Long id) {
        return topographicService.getById(id);
    }

    @GetMapping("/monument/{monumentId}")
    public List<TopographicResponseDto> getByMonument(@PathVariable Long monumentId) {
        return topographicService.getByMonument(monumentId);
    }
}
