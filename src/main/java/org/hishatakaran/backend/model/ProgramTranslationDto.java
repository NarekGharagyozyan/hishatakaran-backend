package org.hishatakaran.backend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramTranslationDto {

    private String title;
    private String description;
    private String program;

    private List<LinkTranslationDto> links;
    private List<ProgramEpisodeTranslationDto> episodes;
    private List<ProgramImageTranslationDto> images;
}