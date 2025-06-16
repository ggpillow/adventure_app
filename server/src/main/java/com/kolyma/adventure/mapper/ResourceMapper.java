package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ResourceDTO;
import com.kolyma.adventure.model.Resource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceMapper {

    public static ResourceDTO toDTO(Resource resource) {
        if (resource == null) return null;
        return new ResourceDTO(resource.getId(), resource.getName());
    }

    public static Resource toEntity(ResourceDTO dto) {
        if (dto == null) return null;
        Resource resource = new Resource();
        resource.setId(dto.getId());
        resource.setName(dto.getName());
        return resource;
    }
}