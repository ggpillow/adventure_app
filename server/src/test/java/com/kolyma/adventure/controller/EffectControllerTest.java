package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.EffectDTO; // если есть DTO
import com.kolyma.adventure.model.Effect;
import com.kolyma.adventure.service.EffectService;
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

class EffectControllerTest {

    @Mock
    private EffectService effectService;

    @InjectMocks
    private EffectController effectController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEffects_ReturnsOk_WhenEffectsExist() {
        List<Effect> effects = List.of(
                new Effect(1L, "Сила", "Увеличивает силу", "positive"),
                new Effect(2L, "Слабость", "Уменьшает силу", "negative")
        );
        when(effectService.getAll()).thenReturn(effects);

        ResponseEntity<List<EffectDTO>> response = effectController.getAllEffects();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Сила", response.getBody().get(0).getName());
    }

    @Test
    void getAllEffects_ReturnsNotFound_WhenNoEffects() {
        when(effectService.getAll()).thenReturn(List.of());

        ResponseEntity<List<EffectDTO>> response = effectController.getAllEffects();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getEffectById_ReturnsOk_WhenFound() {
        Effect effect = new Effect(1L, "Мудрость", "Увеличивает интеллект", "positive");
        when(effectService.getById(1L)).thenReturn(Optional.of(effect));

        ResponseEntity<EffectDTO> response = effectController.getEffectById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Мудрость", response.getBody().getName());
    }

    @Test
    void getEffectById_ReturnsNotFound_WhenNotFound() {
        when(effectService.getById(99L)).thenReturn(Optional.empty());

        ResponseEntity<EffectDTO> response = effectController.getEffectById(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
