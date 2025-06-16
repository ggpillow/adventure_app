package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ItemCombinationDTO;
import com.kolyma.adventure.mapper.ItemCombinationMapper;
import com.kolyma.adventure.model.ItemCombination;
import com.kolyma.adventure.service.ItemCombinationService;
import com.kolyma.adventure.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-combinations")
public class ItemCombinationController {

    private final ItemCombinationService combinationService;
    private final ResourceService resourceService;

    public ItemCombinationController(ItemCombinationService combinationService, ResourceService resourceService) {
        this.combinationService = combinationService;
        this.resourceService = resourceService;
    }

    @GetMapping("/search")
    public ResponseEntity<ItemCombinationDTO> getByResources(@RequestParam Long res1, @RequestParam Long res2) {
        return combinationService.findByResources(res1, res2)
                .map(ItemCombinationMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ItemCombinationDTO>> getAllCombinations() {
        var all = combinationService.getAll()
                .stream()
                .map(ItemCombinationMapper::toDTO)
                .toList();
        return ResponseEntity.ok(all);
    }
}
