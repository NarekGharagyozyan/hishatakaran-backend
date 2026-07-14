package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.service.ProgramService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping(path = "/admin/programs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProgramResponseDto> uploadFiles(
        @RequestPart(value = "data", required = false) String stringData,
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @RequestPart("pdf") MultipartFile pdf,
        @RequestPart("cover") MultipartFile cover
    ){

        return ResponseEntity.ok(programService.postProgram(stringData, images, pdf, cover));

    }

    @PostMapping("/admin/programs/{id}/translate/{language}")
    public ProgramResponseDto translate(
        @PathVariable Long id,
        @PathVariable TranslationLanguage language
    ) {
        return programService.translate(id, language);
    }

    @GetMapping("programs/{id}")
    public ResponseEntity<ProgramResponseDto> findProgramById(
        @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @GetMapping("/programs")
    public ResponseEntity<List<ProgramResponseDto>> findAllProgram(){
        return ResponseEntity.ok(programService.getAllPrograms());
    }
}