package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.SchemeDTO;
import com.kolyma.adventure.mapper.SchemeMapper;
import com.kolyma.adventure.service.SchemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schemes")
public class SchemeController {

    private final SchemeService schemeService;

    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @GetMapping("/scenario/{scenarioId}")
    public ResponseEntity<List<SchemeDTO>> getSchemesByScenario(@PathVariable Long scenarioId) {
        var dtos = schemeService.getByScenarioId(scenarioId).stream()
                .map(SchemeMapper::toDTO)
                .toList();
        if (dtos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<SchemeDTO>> getAllSchemes() {
        var dtos = schemeService.getAll().stream()
                .map(SchemeMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}