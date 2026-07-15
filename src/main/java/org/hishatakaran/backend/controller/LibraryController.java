package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.hishatakaran.backend.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryRepository libraryRepository;
    private final LibraryService libraryService;

    @PostMapping
    public ResponseEntity<LibraryResponseDto> createBook(
        @RequestBody LibraryRequestDto libraryRequestDto
    ){

        return ResponseEntity.ok(libraryService.postBook(libraryRequestDto));

    }

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