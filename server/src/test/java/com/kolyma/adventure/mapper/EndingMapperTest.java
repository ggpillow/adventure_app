package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.EndingDTO;
import com.kolyma.adventure.model.Ending;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndingMapperTest {

    @Test
    void toDto_ShouldMapAllFields() {
        Ending ending = new Ending();
        ending.setId(100L);
        ending.setTitleEnding("Test Title");
        ending.setEndDescr("Test Description");
        ending.setAltQuestion("Alternative question?");
        ending.setScenario(null); // null — допустим

        EndingDTO dto = EndingMapper.toDTO(ending);

        assertNotNull(dto);
        assertEquals(100L, dto.getId());
        assertEquals("Test Title", dto.getTitleEnding());
        assertEquals("Test Description", dto.getEndDescr());
        assertEquals("Alternative question?", dto.getAltQuestion());
        assertNull(dto.getScenarioId());
    }

    @Test
    void toEntity_ShouldMapAllFields() {
        EndingDTO dto = new EndingDTO(
                200L,
                "DTO Title",
                "DTO Description",
                "DTO Alternative?",
                10L
        );

        Ending entity = EndingMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(200L, entity.getId());
        assertEquals("DTO Title", entity.getTitleEnding());
        assertEquals("DTO Description", entity.getEndDescr());
        assertEquals("DTO Alternative?", entity.getAltQuestion());
        // Scenario не задаётся в этом методе
        assertNull(entity.getScenario());
    }

    @Test
    void toDto_ShouldReturnNull_WhenInputIsNull() {
        assertNull(EndingMapper.toDTO(null));
    }

    @Test
    void toEntity_ShouldReturnNull_WhenInputIsNull() {
        assertNull(EndingMapper.toEntity(null));
    }
}