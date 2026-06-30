package org.hishatakaran.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.entity.ProgramLink;
import org.hishatakaran.backend.model.ProgramAiResponseDto;
import org.hishatakaran.backend.model.ProgramRequestDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.service.FileStorageService;
import org.hishatakaran.backend.service.GeminiService;
import org.hishatakaran.backend.service.ProgramService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProgramResponseDto> uploadFiles(
        @RequestPart("data") String stringData,
        @RequestPart("images") List<MultipartFile> images,
        @RequestPart("pdf") MultipartFile pdf,
        @RequestPart("cover") MultipartFile cover
    ){

        return ResponseEntity.ok(programService.postProgram(stringData, images, pdf, cover));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponseDto> findProgramById(
        @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponseDto>> findAllProgram(){
        return ResponseEntity.ok(programService.getAllPrograms());
    }
}