package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ResourceDTO;
import com.kolyma.adventure.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService service;

    public ResourceController(ResourceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}