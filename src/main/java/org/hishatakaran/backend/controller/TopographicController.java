package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.mapper.TopographicMapper;
import org.hishatakaran.backend.model.TopographicResponseDto;
import org.hishatakaran.backend.repository.TopographicRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public TopographicResponseDto getById(@PathVariable Long id) {
        return TopographicMapper.toDto(
            topographicRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/monument/{monumentId}")
    public List<TopographicResponseDto> getByMonument(@PathVariable Long monumentId) {
        return topographicRepository.findByMonumentId(monumentId)
            .stream()
            .map(TopographicMapper::toDto)
            .toList();
    }
}