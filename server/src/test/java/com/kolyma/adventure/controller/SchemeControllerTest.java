package com.kolyma.adventure.controller;

import com.kolyma.adventure.controller.SchemeController;
import com.kolyma.adventure.dto.SchemeDTO;
import com.kolyma.adventure.model.Scheme;
import com.kolyma.adventure.service.SchemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchemeControllerTest {

    @Mock
    private SchemeService schemeService;

    @InjectMocks
    private SchemeController schemeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSchemesByScenario_ReturnsOk_WhenDataExists() {
        Long scenarioId = 1L;
        when(schemeService.getByScenarioId(scenarioId))
                .thenReturn(List.of(new Scheme(1L, null, "img.png")));

        ResponseEntity<List<SchemeDTO>> response = schemeController.getSchemesByScenario(scenarioId);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getSchemesByScenario_ReturnsNotFound_WhenNoData() {
        Long scenarioId = 1L;
        when(schemeService.getByScenarioId(scenarioId))
                .thenReturn(List.of());

        ResponseEntity<List<SchemeDTO>> response = schemeController.getSchemesByScenario(scenarioId);

        assertEquals(404, response.getStatusCodeValue());
    }
}
