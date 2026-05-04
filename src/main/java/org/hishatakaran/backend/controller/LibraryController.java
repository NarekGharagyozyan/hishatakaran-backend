package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryRepository libraryRepository;

    @GetMapping
    public List<Library> getAll() {
        return libraryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Library getById(@PathVariable UUID id) {
        return libraryRepository.findById(id).orElseThrow();
    }
}