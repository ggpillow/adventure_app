package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.SchemeDTO;
import com.kolyma.adventure.model.Scheme;
import com.kolyma.adventure.model.Scenario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchemeMapperTest {

    @Test
    void toDTO_ShouldMapAllFields() {
        Scenario scenario = new Scenario();
        scenario.setId(42L);

        Scheme scheme = new Scheme();
        scheme.setId(1L);
        scheme.setScenario(scenario);
        scheme.setImageSchemes("image/path.png");

        SchemeDTO dto = SchemeMapper.toDTO(scheme);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(42L, dto.getScenarioId());
        assertEquals("image/path.png", dto.getImageSchemes());
    }

    @Test
    void toDTO_ShouldReturnNull_WhenInputIsNull() {
        SchemeDTO dto = SchemeMapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    void toEntity_ShouldMapAllFieldsExceptScenario() {
        SchemeDTO dto = new SchemeDTO(5L, 10L, "some/path.jpg");

        Scheme entity = SchemeMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(5L, entity.getId());
        // scenario не устанавливается в мэппере, проверяем что null
        assertNull(entity.getScenario());
        assertEquals("some/path.jpg", entity.getImageSchemes());
    }

    @Test
    void toEntity_ShouldReturnNull_WhenInputIsNull() {
        Scheme entity = SchemeMapper.toEntity(null);
        assertNull(entity);
    }
}
