package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ParagraphDTO;
import com.kolyma.adventure.model.Effect;
import com.kolyma.adventure.model.Paragraph;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ParagraphMapperTest {

    @Test
    void toDTO_ValidEntity_ReturnsDTO() {
        Effect effect = new Effect(1L, "Сила", "Увеличивает силу", "positive");
        Paragraph paragraph = new Paragraph(10L, 101, "Описание абзаца", effect);

        ParagraphDTO dto = ParagraphMapper.toDTO(paragraph);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals(101, dto.getParagraphNumber());
        assertEquals("Описание абзаца", dto.getParagraphDescr());
        assertEquals(1L, dto.getEffectId());
    }

    @Test
    void toDTO_NullEffect_ReturnsDTOWithNullEffectId() {
        Paragraph paragraph = new Paragraph(11L, 202, "Без эффекта", null);

        ParagraphDTO dto = ParagraphMapper.toDTO(paragraph);

        assertNotNull(dto);
        assertEquals(11L, dto.getId());
        assertEquals(202, dto.getParagraphNumber());
        assertEquals("Без эффекта", dto.getParagraphDescr());
        assertNull(dto.getEffectId());
    }

    @Test
    void toDTO_NullEntity_ReturnsNull() {
        ParagraphDTO dto = ParagraphMapper.toDTO((Paragraph) null);

        assertNull(dto);
    }

    @Test
    void toDTO_OptionalPresent_ReturnsMappedDTO() {
        Paragraph paragraph = new Paragraph(5L, 77, "Абзац", null);

        Optional<ParagraphDTO> result = ParagraphMapper.toDTO(Optional.of(paragraph));

        assertTrue(result.isPresent());
        assertEquals(77, result.get().getParagraphNumber());
    }

    @Test
    void toDTO_OptionalEmpty_ReturnsEmpty() {
        Optional<ParagraphDTO> result = ParagraphMapper.toDTO(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    void toEntity_ValidDTO_ReturnsEntity() {
        ParagraphDTO dto = new ParagraphDTO(20L, 303, "Текст DTO", 2L);

        Paragraph entity = ParagraphMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(20L, entity.getId());
        assertEquals(303, entity.getParagraphNumber());
        assertEquals("Текст DTO", entity.getParagraphDescr());
        assertNull(entity.getEffect()); // effect заполняется отдельно
    }

    @Test
    void toEntity_NullDTO_ReturnsNull() {
        Paragraph entity = ParagraphMapper.toEntity(null);

        assertNull(entity);
    }
}
