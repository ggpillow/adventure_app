package com.kolyma.adventure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
