package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ItemCombinationDTO;
import com.kolyma.adventure.model.ItemCombination;
import com.kolyma.adventure.model.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemCombinationMapperTest {

    @Test
    void toDTO_ShouldMapCorrectly() {
        Resource res1 = new Resource();
        res1.setId(1L);

        Resource res2 = new Resource();
        res2.setId(2L);

        ItemCombination entity = new ItemCombination(10L, res1, res2, "Меч", "sword.png");

        ItemCombinationDTO dto = ItemCombinationMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals(1L, dto.getFirstResourceId());
        assertEquals(2L, dto.getSecondResourceId());
        assertEquals("Меч", dto.getResultItem());
        assertEquals("sword.png", dto.getImageItems());
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        ItemCombinationDTO dto = new ItemCombinationDTO(5L, 3L, 4L, "Щит", "shield.png");

        Resource r1 = new Resource();
        r1.setId(3L);

        Resource r2 = new Resource();
        r2.setId(4L);

        ItemCombination entity = ItemCombinationMapper.toEntity(dto, r1, r2);

        assertNotNull(entity);
        assertEquals(5L, entity.getId());
        assertEquals(r1, entity.getFirstResource());
        assertEquals(r2, entity.getSecondResource());
        assertEquals("Щит", entity.getResultItem());
        assertEquals("shield.png", entity.getImageItems());
    }
}