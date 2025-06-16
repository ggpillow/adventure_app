package com.kolyma.adventure.service;

import com.kolyma.adventure.dto.ResourceDTO;
import com.kolyma.adventure.mapper.ResourceMapper;
import com.kolyma.adventure.model.Resource;
import com.kolyma.adventure.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository repository;

    public ResourceService(ResourceRepository repository) {
        this.repository = repository;
    }

    public List<ResourceDTO> getAll() {
        return repository.findAll().stream().map(ResourceMapper::toDTO).toList();
    }

    public Resource getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ресурс не найден: id = " + id));
    }
}