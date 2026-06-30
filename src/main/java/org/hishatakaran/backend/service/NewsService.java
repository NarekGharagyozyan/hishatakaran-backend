package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.mapper.NewsMapper;
import org.hishatakaran.backend.model.NewsAiResponseDto;
import org.hishatakaran.backend.model.NewsRequestDto;
import org.hishatakaran.backend.model.NewsResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

  private final NewsRepository newsRepository;
  private final FileStorageService fileStorageService;
  private final GeminiService geminiService;
  private final ObjectMapper objectMapper;

  public NewsResponseDto postNews(@ModelAttribute NewsRequestDto newsDto) {
    String result = geminiService.requestGeminiForNews(newsDto.getTitle(), newsDto.getText());
    NewsAiResponseDto newsAiResponseDto = null;
    try {
      var cleanJson = extractJson(result);
      newsAiResponseDto = objectMapper.readValue(cleanJson, NewsAiResponseDto.class);
    }
    catch (JsonProcessingException e)
    {
      e.printStackTrace();
    }
    News news;
    if (newsAiResponseDto != null) {
      news = new News();
      news.setTitleHy(newsAiResponseDto.getTitleHy());
      news.setTitleEn(newsAiResponseDto.getTitleEn());
      news.setTitleFr(newsAiResponseDto.getTitleFr());
      news.setTextHy(newsAiResponseDto.getTextHy());
      news.setTextEn(newsAiResponseDto.getTextEn());
      news.setTextFr(newsAiResponseDto.getTextFr());
      news.setPictures(generateImagePaths(newsDto.getPictures()));
      news.setStatus(Status.DRAFT);
    }
    else {
      throw new RuntimeException("Something went wrong when mapping data from ai model");
    }
    newsRepository.save(news);
    return NewsMapper.toDto(news);
  }

  List<String> generateImagePaths(List<MultipartFile> files) {
    if (files != null) {
      return files.stream()
          .map(file -> fileStorageService.saveImage(file, "news"))
          .toList();
    }
    return null;
  }

  private String extractJson(String response) {
    var start = response.indexOf('{');
    var end = response.lastIndexOf('}');
    if (start == -1 || end == -1 || start >= end) {
      throw new IllegalArgumentException("No valid JSON found in response");
    }
    return response.substring(start, end + 1);
  }
}
