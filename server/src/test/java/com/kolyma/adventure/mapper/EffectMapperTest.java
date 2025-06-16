package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.EffectDTO;
import com.kolyma.adventure.model.Effect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectMapperTest {

    @Test
    void testToDTO() {
        Effect effect = new Effect(1L, "Сила", "Увеличивает силу", "positive");

        EffectDTO dto = EffectMapper.toDTO(effect);

        assertNotNull(dto);
        assertEquals(effect.getId(), dto.getId());
        assertEquals(effect.getName(), dto.getName());
        assertEquals(effect.getDescription(), dto.getDescription());
        assertEquals(effect.getEffectType(), dto.getEffectType());
    }

    @Test
    void testToDTO_Null() {
        EffectDTO dto = EffectMapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    void testToEntity() {
        EffectDTO dto = new EffectDTO(2L, "Слабость", "Уменьшает силу", "negative");

        Effect effect = EffectMapper.toEntity(dto);

        assertNotNull(effect);
        assertEquals(dto.getId(), effect.getId());
        assertEquals(dto.getName(), effect.getName());
        assertEquals(dto.getDescription(), effect.getDescription());
        assertEquals(dto.getEffectType(), effect.getEffectType());
    }

    @Test
    void testToEntity_Null() {
        Effect effect = EffectMapper.toEntity(null);
        assertNull(effect);
    }
}
