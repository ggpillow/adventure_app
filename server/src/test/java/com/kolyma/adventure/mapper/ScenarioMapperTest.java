package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ScenarioDTO;
import com.kolyma.adventure.model.Scenario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioMapperTest {

    @Test
    void toDTO_ShouldMapCorrectly() {
        Scenario scenario = new Scenario(1L, "Title", "Start", "Mini", "img.jpg", "easy");

        ScenarioDTO dto = ScenarioMapper.toDTO(scenario);

        assertEquals(1L, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Start", dto.getStartDescr());
        assertEquals("Mini", dto.getMiniDescription());
        assertEquals("img.jpg", dto.getImageURL());
        assertEquals("easy", dto.getDifficulty());
    }

    @Test
    void toDTO_ShouldReturnNull_WhenInputNull() {
        assertNull(ScenarioMapper.toDTO(null));
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        ScenarioDTO dto = new ScenarioDTO(1L, "Title", "Start", "Mini", "img.jpg", "easy");

        Scenario scenario = ScenarioMapper.toEntity(dto);

        assertEquals(1L, scenario.getId());
        assertEquals("Title", scenario.getTitle());
        assertEquals("Start", scenario.getStartDescr());
        assertEquals("Mini", scenario.getMiniDescription());
        assertEquals("img.jpg", scenario.getImageURL());
        assertEquals("easy", scenario.getDifficulty());
    }

    @Test
    void toEntity_ShouldReturnNull_WhenInputNull() {
        assertNull(ScenarioMapper.toEntity(null));
    }
}