package com.kolyma.adventure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchemeDTO {
    @NotNull
    private Long id;

    @NotNull
    private Long scenarioId; // связываем по ID сценария

    @NotBlank
    private String imageSchemes;
}