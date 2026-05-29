package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.mapper.TopographicMapper;
import org.hishatakaran.backend.model.TopographicResponseDto;
import org.hishatakaran.backend.repository.TopographicRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/topographics")
@RequiredArgsConstructor
public class TopographicController {

    private final TopographicRepository topographicRepository;

    @GetMapping
    public List<TopographicResponseDto> getAll() {
        return topographicRepository.findAll()
            .stream()
            .map(TopographicMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public TopographicResponseDto getById(@PathVariable UUID id) {
        return TopographicMapper.toDto(
            topographicRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/monument/{monumentId}")
    public List<TopographicResponseDto> getByMonument(@PathVariable UUID monumentId) {
        return topographicRepository.findByMonumentId(monumentId)
            .stream()
            .map(TopographicMapper::toDto)
            .toList();
    }
}