package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.mapper.NewsMapper;
import org.hishatakaran.backend.model.NewsRequestDto;
import org.hishatakaran.backend.model.NewsResponseDto;
import org.hishatakaran.backend.repository.NewsRepository;
import org.hishatakaran.backend.service.NewsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsRepository newsRepository;

    @PostMapping(
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public NewsResponseDto postNews(@ModelAttribute NewsRequestDto newsDto) {
        return newsService.postNews(newsDto);
    }

    @GetMapping
    public List<NewsResponseDto> getAll() {
        return newsRepository.findAll()
            .stream()
            .map(NewsMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public NewsResponseDto getById(@PathVariable Long id) {
        return NewsMapper.toDto(
            newsRepository.findById(id).orElseThrow()
        );
    }

}