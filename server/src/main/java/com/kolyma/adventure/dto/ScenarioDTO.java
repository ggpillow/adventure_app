package com.kolyma.adventure.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScenarioDTO {

    @NotNull(message = "Id не должен быть null")
    private Long id;

    @NotBlank(message = "Название сценария не может быть пустым")
    private String title;

    private String startDescr;

    private String miniDescription;

    @NotBlank(message = "URL изображения не может быть пустым")
    private String imageURL;

    @NotBlank(message = "Уровень сложности обязателен")
    private String difficulty;
}