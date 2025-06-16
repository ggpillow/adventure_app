package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.repository.ScenarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScenarioServiceTest {

    @Mock
    private ScenarioRepository repository;

    @InjectMocks
    private ScenarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnScenarios() {
        List<Scenario> scenarios = List.of(
                new Scenario(1L, "Scenario 1", "desc", "mini", "img.jpg", "easy"),
                new Scenario(2L, "Scenario 2", "desc", "mini", "img.jpg", "hard")
        );
        when(repository.findAll()).thenReturn(scenarios);

        List<Scenario> result = service.getAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void getById_ShouldReturnScenario_WhenExists() {
        Scenario scenario = new Scenario(1L, "Scenario", "desc", "mini", "img.jpg", "normal");
        when(repository.findById(1L)).thenReturn(Optional.of(scenario));

        Optional<Scenario> result = service.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Scenario", result.get().getTitle());
        verify(repository).findById(1L);
    }

    @Test
    void getById_ShouldReturnEmpty_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Scenario> result = service.getById(999L);

        assertTrue(result.isEmpty());
        verify(repository).findById(999L);
    }
}