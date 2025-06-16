package com.kolyma.adventure.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParagraphDTO {
    @NotNull
    private Long id;
    private int paragraphNumber;
    private String paragraphDescr;
    private Long effectId;
}