package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryRepository libraryRepository;

    @GetMapping
    public List<LibraryResponseDto> getAll() {
        return libraryRepository.findAll()
            .stream()
            .map(LibraryMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public LibraryResponseDto getById(@PathVariable Long id) {
        return LibraryMapper.toDto(
            libraryRepository.findById(id).orElseThrow()
        );
    }
}