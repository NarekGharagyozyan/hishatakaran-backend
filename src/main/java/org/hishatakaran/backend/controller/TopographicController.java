package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.Topographic;
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
    public List<Topographic> getAll() {
        return topographicRepository.findAll();
    }

    @GetMapping("/{id}")
    public Topographic getById(@PathVariable UUID id) {
        return topographicRepository.findById(id).orElseThrow();
    }

    @GetMapping("/monument/{monumentId}")
    public List<Topographic> getByMonument(@PathVariable UUID monumentId) {
        return topographicRepository.findByMonumentId(monumentId);
    }
}