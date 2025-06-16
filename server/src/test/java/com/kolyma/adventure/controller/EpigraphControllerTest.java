package com.kolyma.adventure.controller;

import com.kolyma.adventure.model.Epigraph;
import com.kolyma.adventure.service.EpigraphService;
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

class EpigraphControllerTest {

    @Mock
    private EpigraphService epigraphService;

    @InjectMocks
    private EpigraphController epigraphController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ReturnsListOfDTOs() {
        List<Epigraph> mockList = List.of(
                new Epigraph(1L, "Мудрость", "Лао-Цзы"),
                new Epigraph(2L, "Думай!", "Кант")
        );
        when(epigraphService.getAll()).thenReturn(mockList);

        ResponseEntity<?> response = epigraphController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getById_ReturnsOk_WhenExists() {
        Epigraph epigraph = new Epigraph(5L, "Сила мысли", "Декарт");
        when(epigraphService.getById(5L)).thenReturn(Optional.of(epigraph));

        ResponseEntity<?> response = epigraphController.getById(5L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getById_ReturnsNotFound_WhenMissing() {
        when(epigraphService.getById(404L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = epigraphController.getById(404L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getRandom_ReturnsOneDTO() {
        Epigraph epigraph = new Epigraph(7L, "Случайная мысль", "Ницше");
        when(epigraphService.getRandom()).thenReturn(epigraph);

        ResponseEntity<?> response = epigraphController.getRandom();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}