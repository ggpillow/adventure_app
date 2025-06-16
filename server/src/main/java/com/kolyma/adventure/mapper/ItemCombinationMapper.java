package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ItemCombinationDTO;
import com.kolyma.adventure.model.ItemCombination;
import com.kolyma.adventure.model.Resource;

public class ItemCombinationMapper {

    public static ItemCombinationDTO toDTO(ItemCombination entity) {
        return new ItemCombinationDTO(
                entity.getId(),
                entity.getFirstResource().getId(),
                entity.getSecondResource().getId(),
                entity.getResultItem(),
                entity.getImageItems()
        );
    }

    public static ItemCombination toEntity(ItemCombinationDTO dto, Resource res1, Resource res2) {
        ItemCombination entity = new ItemCombination();
        entity.setId(dto.getId());
        entity.setFirstResource(res1);
        entity.setSecondResource(res2);
        entity.setResultItem(dto.getResultItem());
        entity.setImageItems(dto.getImageItems());
        return entity;
    }
}