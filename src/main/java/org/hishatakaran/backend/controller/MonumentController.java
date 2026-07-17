package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MonumentEditDto;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentMediasResponseDto;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentTypeEditDto;
import org.hishatakaran.backend.model.MonumentTypeRequestDto;
import org.hishatakaran.backend.model.MonumentTypesResponseDto;
import org.hishatakaran.backend.model.MonumentVideoResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.MonumentTypesRepository;
import org.hishatakaran.backend.service.MonumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MonumentController {

    private final MonumentRepository monumentRepository;
    private final MonumentTypesRepository monumentTypesRepository;
    private final MonumentService monumentService;

    @PostMapping("/admin/monuments")
    public MonumentResponseDto postMonument(
        @RequestBody MonumentRequestDto monumentRequestDto
    ) {
        return monumentService.postMonument(monumentRequestDto);
    }

    @PostMapping("/admin/monuments/{id}/translate/{language}")
    public MonumentResponseDto translate(
        @PathVariable Long id,
        @PathVariable TranslationLanguage language
    ) {
        return monumentService.translate(id, language);
    }

    @PutMapping("/admin/monuments/{id}")
    public MonumentResponseDto updateMonument(
        @PathVariable Long id,
        @RequestBody MonumentEditDto monumentRequestDto
    ){
        return monumentService.updateMonument(id, monumentRequestDto);
    }

    @PostMapping("/admin/monuments/{id}/publish")
    public MonumentResponseDto publishMonument(
        @PathVariable Long id
    ) {
        return monumentService.publish(id);
    }

    @DeleteMapping("/admin/monuments/{id}")
    public void deleteMonument(@PathVariable Long id) {
        monumentService.deleteMonument(id);
    }

    @PostMapping("/admin/monuments/uploadImages")
    public List<String> uploadImages(
        @RequestPart(value = "images") List<MultipartFile> images
    ) {
        return monumentService.generateImagesPaths(images);
    }

    @PostMapping("/monuments/uploadMeasurements")
    public List<String> uploadMeasurements(
        @RequestPart(value = "measurements") List<MultipartFile> measurements
    ) {
        return monumentService.generateMeasurementsPaths(measurements);
    }

    @GetMapping("/monuments")
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

    @GetMapping("/monuments/{id}")
    public MonumentResponseDto getById(@PathVariable Long id) {
        return MonumentMapper.toDto(
            monumentRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/monuments/region/{regionId}")
    public List<MonumentResponseDto> getByRegion(@PathVariable Long regionId) {
        return monumentRepository.findByRegionId(regionId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/monuments/settlement/{settlementId}")
    public List<MonumentResponseDto> getBySettlement(@PathVariable Long settlementId) {
        return monumentRepository.findBySettlementId(settlementId)
            .stream()
            .map(MonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/monuments/types")
    public List<MonumentTypesResponseDto> getAllMonumentTypes() {
        return monumentTypesRepository.findAll()
            .stream()
            .map(monumentType -> new MonumentTypesResponseDto(
                monumentType.getId(),
                LanguagesResponseDto.of(
                    monumentType.getNameHy(),
                    monumentType.getNameEn(),
                    monumentType.getNameFr()
                )
            ))
            .distinct()
            .toList();
    }

    @GetMapping("/monuments/images")
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

    @GetMapping("/monuments/videos")
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

    @GetMapping("/monuments/measurements")
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

    @PostMapping("/admin/monuments/types")
    public MonumentTypesResponseDto addNewMonumentType(
        @RequestBody MonumentTypeRequestDto monumentTypeRequestDto
    ) {
        return monumentService.createNewMonumentType(monumentTypeRequestDto);
    }

    @PutMapping("/admin/monuments/types/{monumentTypeId}")
    public MonumentTypesResponseDto editMonumentType(
        @PathVariable Long monumentTypeId,
        @RequestBody MonumentTypeEditDto monumentTypeEditDto
    ) {
        return monumentService.editMonumentType(monumentTypeId, monumentTypeEditDto);
    }

    @DeleteMapping("/admin/monuments/types/{monumentTypeId}")
    public void deleteMonumentType(@PathVariable Long monumentTypeId) {
        monumentTypesRepository.deleteById(monumentTypeId);
    }

}