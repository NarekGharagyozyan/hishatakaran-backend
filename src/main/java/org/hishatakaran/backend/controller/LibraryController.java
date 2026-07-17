package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.model.LibraryEditDto;
import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.hishatakaran.backend.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryRepository libraryRepository;
    private final LibraryService libraryService;

    @PostMapping("/admin/library")
    public ResponseEntity<LibraryResponseDto> createBook(
        @RequestBody LibraryRequestDto libraryRequestDto
    ){

        return ResponseEntity.ok(libraryService.postBook(libraryRequestDto));

    }

    @PostMapping("/admin/library/{id}/translate/{language}")
    public LibraryResponseDto translate(
        @PathVariable Long id,
        @PathVariable TranslationLanguage language
    ) {
        return libraryService.translate(id, language);
    }

    @PutMapping("/admin/library/{id}")
    public LibraryResponseDto editLibrary(
        @PathVariable Long id,
        @RequestBody LibraryEditDto libraryEditDto
    ) {
        return libraryService.editLibrary(id, libraryEditDto);
    }

    @DeleteMapping("/admin/library/{id}")
    public void deleteLibrary(@PathVariable Long id) {
        libraryService.deleteLibrary(id);
    }

    @GetMapping("/library")
    public List<LibraryResponseDto> getAll() {
        return libraryRepository.findAll()
            .stream()
            .map(LibraryMapper::toDto)
            .toList();
    }

    @GetMapping("/library/{id}")
    public LibraryResponseDto getById(@PathVariable Long id) {
        return LibraryMapper.toDto(
            libraryRepository.findById(id).orElseThrow()
        );
    }
}