package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ResourceDTO;
import com.kolyma.adventure.model.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceMapperTest {

    @Test
    void toDTO_ValidEntity_ReturnsDTO() {
        Resource resource = new Resource(1L, "Камень");

        ResourceDTO dto = ResourceMapper.toDTO(resource);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Камень", dto.getName());
    }

    @Test
    void toDTO_Null_ReturnsNull() {
        assertNull(ResourceMapper.toDTO(null));
    }

    @Test
    void toEntity_ValidDTO_ReturnsEntity() {
        ResourceDTO dto = new ResourceDTO(2L, "Железо");

        Resource resource = ResourceMapper.toEntity(dto);

        assertNotNull(resource);
        assertEquals(2L, resource.getId());
        assertEquals("Железо", resource.getName());
    }

    @Test
    void toEntity_Null_ReturnsNull() {
        assertNull(ResourceMapper.toEntity(null));
    }
}