package org.hishatakaran.backend.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class NewsRequestDto {

    private final String title;
    private final String text;
    private final List<MultipartFile> pictures;
}