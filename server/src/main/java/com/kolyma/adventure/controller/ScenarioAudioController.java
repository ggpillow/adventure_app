package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ScenarioAudioDTO;
import com.kolyma.adventure.mapper.ScenarioAudioMapper;
import com.kolyma.adventure.service.ScenarioAudioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scenario-audio")
public class ScenarioAudioController {

    private final ScenarioAudioService audioService;

    public ScenarioAudioController(ScenarioAudioService audioService) {
        this.audioService = audioService;
    }

    @GetMapping("/by-scenario/{scenarioId}")
    public ResponseEntity<List<ScenarioAudioDTO>> getByScenario(@PathVariable Long scenarioId) {
        var audios = audioService.getByScenarioId(scenarioId)
                .stream()
                .map(ScenarioAudioMapper::toDTO)
                .toList();
        return ResponseEntity.ok(audios);
    }
}