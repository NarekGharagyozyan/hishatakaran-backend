package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MonumentEditDto;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentMediasResponseDto;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentTypesResponseDto;
import org.hishatakaran.backend.model.MonumentVideoResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.service.MonumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

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

    @PostMapping("/{id}/translate/{language}")
    public MonumentResponseDto translate(
        @PathVariable Long id,
        @PathVariable TranslationLanguage language
    ) {
        return monumentService.translate(id, language);
    }

    @PutMapping("/{id}")
    public MonumentResponseDto updateMonument(
        @PathVariable Long id,
        @RequestBody MonumentEditDto monumentRequestDto
    ){
        return monumentService.updateMonument(id, monumentRequestDto);
    }

    @PostMapping("/{id}/publish")
    public MonumentResponseDto publishMonument(
        @PathVariable Long id
    ) {
        return monumentService.publish(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMonument(@PathVariable Long id) {
        monumentService.deleteMonument(id);
    }

    @PostMapping("/uploadImages")
    public List<String> uploadImages(
        @RequestPart(value = "images") List<MultipartFile> images
    ) {
        return monumentService.generateImagesPaths(images);
    }

    @PostMapping("/uploadMeasurements")
    public List<String> uploadMeasurements(
        @RequestPart(value = "measurements") List<MultipartFile> measurements
    ) {
        return monumentService.generateMeasurementsPaths(measurements);
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
        String monumentType
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

    @GetMapping("/types")
    public List<MonumentTypesResponseDto> getAllMonumentTypes() {
        List<LanguagesResponseDto> types = monumentRepository.findAll()
            .stream()
            .map(monument -> new LanguagesResponseDto(
                monument.getMonumentTypeHy(),
                monument.getMonumentTypeEn(),
                monument.getMonumentTypeFr()
            ))
            .distinct()
            .toList();

        return IntStream.range(0, types.size())
            .mapToObj(i -> new MonumentTypesResponseDto(
                (long) (i + 1),
                types.get(i)
            ))
            .toList();
    }

    @GetMapping("/images")
    public List<MonumentMediasResponseDto> getMonumentImages(
        @RequestParam(required = false)
        Long regionId,
        @RequestParam(required = false)
        Long settlementId,
        @RequestParam(required = false)
        String monumentType
    ) {
        MonumentFilterRequest request =
            new MonumentFilterRequest();

        request.setRegionId(regionId);
        request.setSettlementId(settlementId);
        request.setMonumentType(monumentType);

        List<String> images = monumentService.filter(request)
            .stream()
            .map(MonumentResponseDto::getImages)
            .flatMap(Collection::stream)
            .toList();

        return IntStream.range(0, images.size())
            .mapToObj(i -> new MonumentMediasResponseDto(
                (long) i + 1,
                images.get(i)
            ))
            .toList();
    }

    @GetMapping("/videos")
    public List<MonumentVideoResponseDto> getMonumentVideos(
        @RequestParam(required = false)
        Long regionId,
        @RequestParam(required = false)
        Long settlementId,
        @RequestParam(required = false)
        String monumentType
    ) {
        MonumentFilterRequest request =
            new MonumentFilterRequest();

        request.setRegionId(regionId);
        request.setSettlementId(settlementId);
        request.setMonumentType(monumentType);

        return monumentService.filter(request)
            .stream()
            .map(MonumentResponseDto::getVideos)
            .flatMap(Collection::stream)
            .toList();
    }

    @GetMapping("/measurements")
    public List<MonumentMediasResponseDto> getMonumentMeasurements(
        @RequestParam(required = false)
        Long regionId,
        @RequestParam(required = false)
        Long settlementId,
        @RequestParam(required = false)
        String monumentType
    ) {
        MonumentFilterRequest request =
            new MonumentFilterRequest();

        request.setRegionId(regionId);
        request.setSettlementId(settlementId);
        request.setMonumentType(monumentType);

        List<String> measurements = monumentService.filter(request)
            .stream()
            .map(MonumentResponseDto::getMeasurements)
            .flatMap(Collection::stream)
            .toList();

        return IntStream.range(0, measurements.size())
            .mapToObj(i -> new MonumentMediasResponseDto(
                (long) i + 1,
                measurements.get(i)
            ))
            .toList();
    }

}