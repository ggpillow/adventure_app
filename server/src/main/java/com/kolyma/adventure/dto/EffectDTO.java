package com.kolyma.adventure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EffectDTO {

    private Long id;

    @NotBlank(message = "Имя эффекта обязательно")
    private String name;

    private String description;

    @NotBlank(message = "Тип эффекта обязателен")
    private String effectType; // positive, negative, choice
}