package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.EffectDTO;
import com.kolyma.adventure.mapper.EffectMapper;
import com.kolyma.adventure.service.EffectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/effects")
public class EffectController {

    private final EffectService effectService;

    public EffectController(EffectService effectService) {
        this.effectService = effectService;
    }

    @GetMapping
    public ResponseEntity<List<EffectDTO>> getAllEffects() {
        var dtos = effectService.getAll().stream()
                .map(EffectMapper::toDTO)
                .toList();
        if (dtos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EffectDTO> getEffectById(@PathVariable Long id) {
        Optional<EffectDTO> dto = effectService.getById(id)
                .map(EffectMapper::toDTO);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}