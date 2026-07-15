package org.hishatakaran.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FootnoteRequestDto {

    private Long orderNumber;
    private String text;
}