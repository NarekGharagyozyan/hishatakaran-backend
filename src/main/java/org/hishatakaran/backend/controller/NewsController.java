package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.NewsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;

    @GetMapping
    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @GetMapping("/{id}")
    public News getById(@PathVariable UUID id) {
        return newsRepository.findById(id).orElseThrow();
    }

    @GetMapping("/status/{status}")
    public List<News> getByStatus(@PathVariable Status status) {
        return newsRepository.findByStatus(status);
    }
}