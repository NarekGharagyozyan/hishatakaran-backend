package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.repository.BibliographyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bibliography")
@RequiredArgsConstructor
public class BibliographyController {

    private final BibliographyRepository bibliographyRepository;

    @GetMapping
    public List<Bibliography> getAll() {
        return bibliographyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Bibliography getById(@PathVariable UUID id) {
        return bibliographyRepository.findById(id).orElseThrow();
    }

    @GetMapping("/monument/{monumentId}")
    public List<Bibliography> getByMonument(@PathVariable UUID monumentId) {
        return bibliographyRepository.findByMonumentId(monumentId);
    }
}