package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.EffectDTO;
import com.kolyma.adventure.model.Effect;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EffectMapper {

    public static EffectDTO toDTO(Effect effect) {
        if (effect == null) return null;
        return new EffectDTO(
                effect.getId(),
                effect.getName(),
                effect.getDescription(),
                effect.getEffectType()
        );
    }

    public static Effect toEntity(EffectDTO dto) {
        if (dto == null) return null;
        Effect effect = new Effect();
        effect.setId(dto.getId());
        effect.setName(dto.getName());
        effect.setDescription(dto.getDescription());
        effect.setEffectType(dto.getEffectType());
        return effect;
    }
}