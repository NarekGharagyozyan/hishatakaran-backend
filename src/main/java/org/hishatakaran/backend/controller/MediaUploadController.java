package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.EntityType;
import org.hishatakaran.backend.model.MediaType;
import org.hishatakaran.backend.service.FileStorageService;
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
public class MediaUploadController {

  private final FileStorageService fileStorageService;

  @PostMapping("/admin/{entityType}/uploadMedia/{mediaType}")
  public List<String> uploadImages(
      @PathVariable EntityType entityType,
      @PathVariable MediaType mediaType,
      @RequestPart(value = "media") List<MultipartFile> media
  ) {
    return fileStorageService.uploadMedia(media, entityType, mediaType);
  }
}
