package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.EndingDTO;
import com.kolyma.adventure.mapper.EndingMapper;
import com.kolyma.adventure.service.EndingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endings")
public class EndingController {

    private final EndingService endingService;

    public EndingController(EndingService endingService) {
        this.endingService = endingService;
    }

    // Получить список концовок по ID сценария
    @GetMapping("/scenario/{scenarioId}")
    public ResponseEntity<List<EndingDTO>> getEndingsByScenario(@PathVariable Long scenarioId) {
        var dtos = endingService.getByScenarioId(scenarioId).stream()
                .map(EndingMapper::toDTO)
                .toList();
        if (dtos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dtos);
    }

    // Получить одну концовку по ID сценария и названию концовки
    @GetMapping("/scenario/{scenarioId}/title")
    public ResponseEntity<EndingDTO> getEndingByScenarioAndTitle(
            @PathVariable Long scenarioId,
            @RequestParam String titleEnding
    ) {
        try {
            var ending = endingService.getByScenarioIdAndTitle(scenarioId, titleEnding);
            return ResponseEntity.ok(EndingMapper.toDTO(ending));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // или верни текст ошибки через badRequest()
        }
    }
}
