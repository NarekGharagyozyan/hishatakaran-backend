package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.Color;
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
    public List<DescriptiveCharacteristicReference> getAll() {
        return descriptiveRepository.findAll();
    }

    @GetMapping("/{id}")
    public DescriptiveCharacteristicReference getById(@PathVariable UUID id) {
        return descriptiveRepository.findById(id).orElseThrow();
    }

    @GetMapping("/monument/{monumentId}")
    public List<DescriptiveCharacteristicReference> getByMonument(@PathVariable UUID monumentId) {
        return descriptiveRepository.findByMonumentId(monumentId);
    }

    @GetMapping("/color/{color}")
    public List<DescriptiveCharacteristicReference> getByColor(@PathVariable Color color) {
        return descriptiveRepository.findByColor(color);
    }
}