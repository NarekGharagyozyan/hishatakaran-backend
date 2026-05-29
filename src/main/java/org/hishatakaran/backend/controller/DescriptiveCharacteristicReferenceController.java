package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.DescriptiveCharacteristicMapper;
import org.hishatakaran.backend.model.Color;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.repository.DescriptiveCharacteristicReferenceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/descriptive-characteristics")
@RequiredArgsConstructor
public class DescriptiveCharacteristicReferenceController {

    private final DescriptiveCharacteristicReferenceRepository descriptiveRepository;

    @GetMapping
    public List<DescriptiveCharacteristicResponseDto> getAll() {
        return descriptiveRepository.findAll()
            .stream()
            .map(DescriptiveCharacteristicMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public DescriptiveCharacteristicResponseDto getById(@PathVariable UUID id) {
        return DescriptiveCharacteristicMapper.toDto(
            descriptiveRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/monument/{monumentId}")
    public List<DescriptiveCharacteristicResponseDto> getByMonument(@PathVariable UUID monumentId) {
        return descriptiveRepository.findByMonumentId(monumentId)
            .stream()
            .map(DescriptiveCharacteristicMapper::toDto)
            .toList();
    }

    @GetMapping("/color/{color}")
    public List<DescriptiveCharacteristicResponseDto> getByColor(@PathVariable Color color) {
        return descriptiveRepository.findByColor(color)
            .stream()
            .map(DescriptiveCharacteristicMapper::toDto)
            .toList();
    }
}