package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentType;
import org.hishatakaran.backend.model.NewsRequestDto;
import org.hishatakaran.backend.model.NewsResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.service.MonumentService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/monuments")
@RequiredArgsConstructor
public class MonumentController {

    private final MonumentRepository monumentRepository;
    private final MonumentService monumentService;

    @PostMapping
    public MonumentResponseDto postNews(@RequestBody MonumentRequestDto monumentRequestDto) {
        return monumentService.postMonument(monumentRequestDto);
    }

    /*@GetMapping
    public List<MonumentResponseDto> getAll() {
        return monumentRepository.findAll()
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }*/

    @GetMapping
    public List<MonumentResponseDto> getMonumentsByFilter(
        @RequestParam(required = false)
        Integer regionId,
        @RequestParam(required = false)
        Integer settlementId,
        @RequestParam(required = false)
        MonumentType monumentType
    ) {

        MonumentFilterRequest request =
            new MonumentFilterRequest();

        request.setRegionId(regionId);
        request.setSettlementId(settlementId);
        request.setMonumentType(monumentType);

        return monumentService.filter(request);
    }

    @GetMapping("/{id}")
    public MonumentResponseDto getById(@PathVariable UUID id) {
        return MonumentMapper.toDto(
            monumentRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/status/{status}")
    public List<MonumentResponseDto> getByStatus(@PathVariable Status status) {
        return monumentRepository.findByStatus(status)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/region/{regionId}")
    public List<MonumentResponseDto> getByRegion(@PathVariable Integer regionId) {
        return monumentRepository.findByRegionId(regionId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/settlement/{settlementId}")
    public List<MonumentResponseDto> getBySettlement(@PathVariable Integer settlementId) {
        return monumentRepository.findBySettlementId(settlementId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/type/{type}")
    public List<MonumentResponseDto> getByType(@PathVariable MonumentType type) {
        return monumentRepository.findByMonumentTypeArmenianOrMonumentTypeEnglishOrMonumentTypeEnglish(type, type, type)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

}