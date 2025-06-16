package com.kolyma.adventure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScenarioAudioDTO {
    private Long id;

    @NotBlank
    private String audioUrl;

    @NotNull
    private Long scenarioId;
}