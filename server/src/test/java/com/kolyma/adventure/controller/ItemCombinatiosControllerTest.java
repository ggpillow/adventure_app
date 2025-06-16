package com.kolyma.adventure.controller;

import com.kolyma.adventure.model.ItemCombination;
import com.kolyma.adventure.model.Resource;
import com.kolyma.adventure.service.ItemCombinationService;
import com.kolyma.adventure.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemCombinationControllerTest {

    @Mock
    private ItemCombinationService combinationService;

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ItemCombinationController combinationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByResources_ReturnsOk_WhenExists() {
        Resource res1 = new Resource();
        res1.setId(1L);
        Resource res2 = new Resource();
        res2.setId(2L);
        ItemCombination combo = new ItemCombination(1L, res1, res2, "Комбинированный предмет", "img.png");

        when(combinationService.findByResources(1L, 2L)).thenReturn(Optional.of(combo));

        ResponseEntity<?> response = combinationController.getByResources(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getByResources_ReturnsNotFound_WhenMissing() {
        when(combinationService.findByResources(1L, 2L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = combinationController.getByResources(1L, 2L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getAllCombinations_ReturnsList() {
        List<ItemCombination> list = List.of(
                new ItemCombination(1L, new Resource(), new Resource(), "Предмет", "img.png")
        );
        when(combinationService.getAll()).thenReturn(list);

        ResponseEntity<?> response = combinationController.getAllCombinations();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}