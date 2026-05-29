package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.mapper.NewsMapper;
import org.hishatakaran.backend.model.NewsRequestDto;
import org.hishatakaran.backend.model.NewsResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.NewsRepository;
import org.hishatakaran.backend.service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;
    private final FileStorageService fileStorageService;

    @PostMapping(
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public NewsResponseDto postNews(@ModelAttribute NewsRequestDto newsDto) {

        List<String> imagePaths = new ArrayList<>();

        if (newsDto.getPictures() != null) {

            imagePaths = newsDto.getPictures()
                .stream()
                .map(fileStorageService::saveNewsImage)
                .toList();
        }

        News news = new News();

        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setPictures(imagePaths);
        news.setStatus(Status.DRAFT);

        newsRepository.save(news);

        return NewsMapper.toDto(news);
    }

    @GetMapping
    public List<NewsResponseDto> getAll() {
        return newsRepository.findAll()
            .stream()
            .map(NewsMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public NewsResponseDto getById(@PathVariable UUID id) {
        return NewsMapper.toDto(
            newsRepository.findById(id).orElseThrow()
        );
    }

    @GetMapping("/status/{status}")
    public List<NewsResponseDto> getByStatus(@PathVariable Status status) {
        return newsRepository.findByStatus(status)
            .stream()
            .map(NewsMapper::toDto)
            .toList();
    }
}