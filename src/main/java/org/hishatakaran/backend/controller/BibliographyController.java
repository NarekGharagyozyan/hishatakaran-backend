//package org.hishatakaran.backend.controller;
//
//import lombok.RequiredArgsConstructor;
//
//import org.hishatakaran.backend.mapper.BibliographyMapper;
//import org.hishatakaran.backend.model.BibliographyResponseDto;
//import org.hishatakaran.backend.repository.BibliographyRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/bibliography")
//@RequiredArgsConstructor
//public class BibliographyController {
//
//    private final BibliographyRepository bibliographyRepository;
//
//    @GetMapping
//    public List<BibliographyResponseDto> getAll() {
//        return bibliographyRepository.findAll()
//            .stream()
//            .map(BibliographyMapper::toDto)
//            .toList();
//    }
//
//    @GetMapping("/{id}")
//    public BibliographyResponseDto getById(@PathVariable UUID id) {
//        return BibliographyMapper.toDto(
//            bibliographyRepository.findById(id).orElseThrow()
//        );
//    }
//
//    @GetMapping("/monument/{monumentId}")
//    public List<BibliographyResponseDto> getByMonument(@PathVariable UUID monumentId) {
//        return bibliographyRepository.findByMonumentId(monumentId)
//            .stream()
//            .map(BibliographyMapper::toDto)
//            .toList();
//    }
//}