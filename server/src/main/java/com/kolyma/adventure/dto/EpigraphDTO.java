package com.kolyma.adventure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EpigraphDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String quote;

    @NotBlank
    private String author;
}