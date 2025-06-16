package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.EpigraphDTO;
import com.kolyma.adventure.model.Epigraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpigraphMapperTest {

    @Test
    void toDTO_ValidEntity_ReturnsDTO() {
        Epigraph epigraph = new Epigraph(1L, "Цитата", "Автор");

        EpigraphDTO dto = EpigraphMapper.toDTO(epigraph);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Цитата", dto.getQuote());
        assertEquals("Автор", dto.getAuthor());
    }

    @Test
    void toDTO_Null_ReturnsNull() {
        assertNull(EpigraphMapper.toDTO(null));
    }

    @Test
    void toEntity_ValidDTO_ReturnsEntity() {
        EpigraphDTO dto = new EpigraphDTO(2L, "Смысл жизни", "Дуглас Адамс");

        Epigraph entity = EpigraphMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals("Смысл жизни", entity.getQuote());
        assertEquals("Дуглас Адамс", entity.getAuthor());
    }

    @Test
    void toEntity_Null_ReturnsNull() {
        assertNull(EpigraphMapper.toEntity(null));
    }
}