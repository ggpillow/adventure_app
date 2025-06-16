package com.kolyma.adventure.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCombinationDTO {
    private Long id;

    @NotNull
    private Long firstResourceId;

    @NotNull
    private Long secondResourceId;

    @NotNull
    private String resultItem;

    @NotNull
    private String imageItems;
}