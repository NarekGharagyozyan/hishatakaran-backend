package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.RegionMapper;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.repository.RegionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionRepository regionRepository;

    @GetMapping
    public List<RegionResponseDto> getAll() {
        return regionRepository.findAll()
            .stream()
            .map(RegionMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public RegionResponseDto getById(@PathVariable Integer id) {
        return RegionMapper.toDto(
            regionRepository.findById(id).orElseThrow()
        );
    }
}