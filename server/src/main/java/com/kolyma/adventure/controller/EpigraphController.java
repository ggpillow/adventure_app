package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.EpigraphDTO;
import com.kolyma.adventure.mapper.EpigraphMapper;
import com.kolyma.adventure.service.EpigraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epigraphs")
public class EpigraphController {

    private final EpigraphService epigraphService;

    public EpigraphController(EpigraphService epigraphService) {
        this.epigraphService = epigraphService;
    }

    @GetMapping
    public ResponseEntity<List<EpigraphDTO>> getAll() {
        var list = epigraphService.getAll().stream()
                .map(EpigraphMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/random")
    public ResponseEntity<EpigraphDTO> getRandom() {
        return ResponseEntity.ok(EpigraphMapper.toDTO(epigraphService.getRandom()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpigraphDTO> getById(@PathVariable Long id) {
        return epigraphService.getById(id)
                .map(EpigraphMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}