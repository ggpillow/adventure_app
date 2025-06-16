package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.SchemeDTO;
import com.kolyma.adventure.model.Scheme;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SchemeMapper {

    public static SchemeDTO toDTO(Scheme scheme) {
        if (scheme == null) return null;
        return new SchemeDTO(
                scheme.getId(),
                scheme.getScenario() != null ? scheme.getScenario().getId() : null,
                scheme.getImageSchemes()
        );
    }

    public static Scheme toEntity(SchemeDTO dto) {
        if (dto == null) return null;
        Scheme scheme = new Scheme();
        scheme.setId(dto.getId());
        // Обратите внимание: scenario нужно установить отдельно, например в сервисе
        scheme.setImageSchemes(dto.getImageSchemes());
        return scheme;
    }
}