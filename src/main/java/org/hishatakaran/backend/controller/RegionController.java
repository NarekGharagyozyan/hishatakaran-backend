package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.service.RegionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public List<RegionResponseDto> getAll() {
        return regionService.getAll();
    }

    @GetMapping("/{id}")
    public RegionResponseDto getById(@PathVariable Long id) {
        return regionService.getById(id);
    }
}
