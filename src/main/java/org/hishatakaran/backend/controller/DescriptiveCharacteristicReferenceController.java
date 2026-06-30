package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.DescriptiveCharacteristicMapper;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.repository.DescriptiveCharacteristicReferenceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public DescriptiveCharacteristicResponseDto getById(@PathVariable Long id) {
        return DescriptiveCharacteristicMapper.toDto(
            descriptiveRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/monument/{monumentId}")
    public List<DescriptiveCharacteristicResponseDto> getByMonument(@PathVariable Long monumentId) {
        return descriptiveRepository.findByMonumentId(monumentId)
            .stream()
            .map(DescriptiveCharacteristicMapper::toDto)
            .toList();
    }
}