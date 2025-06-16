package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ScenarioDTO;
import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.service.ScenarioService;
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

class ScenarioControllerTest {

    @Mock
    private ScenarioService service;

    @InjectMocks
    private ScenarioController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllScenarios_ReturnsOk_WhenDataExists() {
        List<Scenario> scenarios = List.of(new Scenario(1L, "Title", "Start", "Mini", "img.jpg", "easy"));
        when(service.getAll()).thenReturn(scenarios);

        ResponseEntity<List<ScenarioDTO>> response = controller.getAllScenarios();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getAllScenarios_ReturnsNotFound_WhenEmpty() {
        when(service.getAll()).thenReturn(List.of());

        ResponseEntity<List<ScenarioDTO>> response = controller.getAllScenarios();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getScenarioById_ReturnsOk_WhenFound() {
        Scenario scenario = new Scenario(1L, "Title", "Start", "Mini", "img.jpg", "easy");
        when(service.getById(1L)).thenReturn(Optional.of(scenario));

        ResponseEntity<?> response = controller.getScenarioById(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getScenarioById_ReturnsNotFound_WhenMissing() {
        when(service.getById(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getScenarioById(999L);

        assertEquals(404, response.getStatusCodeValue());
    }
}