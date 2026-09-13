package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.model.RegionEditDto;
import org.hishatakaran.backend.model.RegionRequestDto;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.service.RegionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/regions")
    public List<RegionResponseDto> getAll() {
        return regionService.getAll();
    }

    @GetMapping("/regions/{id}")
    public RegionResponseDto getById(@PathVariable Long id) {
        return regionService.getById(id);
    }

    @PostMapping("/admin/regions")
    public RegionResponseDto addNewRegion(
        @RequestBody RegionRequestDto regionRequestDto
    ) {
        return regionService.createNewRegion(regionRequestDto);
    }

    @PutMapping("/admin/regions/{regionId}")
    public RegionResponseDto editRegion(
        @PathVariable Long regionId,
        @RequestBody RegionEditDto regionEditDto
    ) {
        return regionService.editRegion(regionId, regionEditDto);
    }

    @DeleteMapping("/admin/regions/{regionId}")
    public void deleteRegion(@PathVariable Long regionId) {
        regionService.deleteRegion(regionId);
    }
}
