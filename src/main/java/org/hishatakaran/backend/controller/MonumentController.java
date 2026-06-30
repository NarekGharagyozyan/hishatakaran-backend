package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentType;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.service.MonumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/monuments")
@RequiredArgsConstructor
public class MonumentController {

    private final MonumentRepository monumentRepository;
    private final MonumentService monumentService;

    @PostMapping
    public MonumentResponseDto postMonument(
        @RequestBody MonumentRequestDto monumentRequestDto
    ) {
        return monumentService.postMonument(monumentRequestDto);
    }

    @PostMapping("/uploadImages")
    public List<String> uploadImages(
        @RequestPart(value = "pictures") List<MultipartFile> pictures
    ) {
        return monumentService.generateImagePaths(pictures);
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
        Long regionId,
        @RequestParam(required = false)
        Long settlementId,
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
    public MonumentResponseDto getById(@PathVariable Long id) {
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
    public List<MonumentResponseDto> getByRegion(@PathVariable Long regionId) {
        return monumentRepository.findByRegionId(regionId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/settlement/{settlementId}")
    public List<MonumentResponseDto> getBySettlement(@PathVariable Long settlementId) {
        return monumentRepository.findBySettlementId(settlementId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/type/{type}")
    public List<MonumentResponseDto> getByType(@PathVariable String type) {
        return monumentRepository.findByMonumentTypeHyOrMonumentTypeEnOrMonumentTypeFr(type, type, type)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

}