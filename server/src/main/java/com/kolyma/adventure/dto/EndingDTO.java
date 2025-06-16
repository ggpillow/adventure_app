package com.kolyma.adventure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EndingDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String titleEnding;

    @NotBlank
    private String endDescr;

    private String altQuestion;

    @NotNull
    private Long scenarioId;
}
