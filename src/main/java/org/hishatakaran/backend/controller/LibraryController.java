package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.hishatakaran.backend.service.LibraryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryRepository libraryRepository;
    private final LibraryService libraryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LibraryResponseDto> uploadFiles(
        @RequestPart("data") String stringData,
        @RequestPart("pdf") MultipartFile pdf,
        @RequestPart("cover") MultipartFile cover
    ){

        return ResponseEntity.ok(libraryService.postBook(stringData, pdf, cover));

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