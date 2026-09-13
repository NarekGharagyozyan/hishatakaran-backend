package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.service.DescriptiveCharacteristicReferenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descriptive-characteristics")
@RequiredArgsConstructor
public class DescriptiveCharacteristicReferenceController {

    private final DescriptiveCharacteristicReferenceService descriptiveService;

    @GetMapping
    public List<DescriptiveCharacteristicResponseDto> getAll() {
        return descriptiveService.getAll();
    }

    @GetMapping("/{id}")
    public DescriptiveCharacteristicResponseDto getById(@PathVariable Long id) {
        return descriptiveService.getById(id);
    }

    @GetMapping("/monument/{monumentId}")
    public List<DescriptiveCharacteristicResponseDto> getByMonument(@PathVariable Long monumentId) {
        return descriptiveService.getByMonument(monumentId);
    }
}
