package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ScenarioDTO;
import com.kolyma.adventure.mapper.ScenarioMapper;
import com.kolyma.adventure.service.ScenarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scenarios")
public class ScenarioController {

    private final ScenarioService scenarioService;

    public ScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping
    public ResponseEntity<List<ScenarioDTO>> getAllScenarios() {
        var dtos = scenarioService.getAll().stream()
                .map(ScenarioMapper::toDTO)
                .toList();
        if (dtos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScenarioDTO> getScenarioById(@PathVariable Long id) {
        return scenarioService.getById(id)
                .map(ScenarioMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}