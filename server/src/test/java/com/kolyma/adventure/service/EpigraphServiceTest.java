package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Epigraph;
import com.kolyma.adventure.repository.EpigraphRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EpigraphServiceTest {

    @Mock
    private EpigraphRepository epigraphRepository;

    @InjectMocks
    private EpigraphService epigraphService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnList() {
        List<Epigraph> mockList = List.of(
                new Epigraph(1L, "Цитата 1", "Автор 1"),
                new Epigraph(2L, "Цитата 2", "Автор 2")
        );

        when(epigraphRepository.findAll()).thenReturn(mockList);

        List<Epigraph> result = epigraphService.getAll();

        assertEquals(2, result.size());
        verify(epigraphRepository).findAll();
    }

    @Test
    void getById_ShouldReturnEpigraphIfExists() {
        Epigraph epigraph = new Epigraph(3L, "Цитата по ID", "Автор");
        when(epigraphRepository.findById(3L)).thenReturn(Optional.of(epigraph));

        Optional<Epigraph> result = epigraphService.getById(3L);

        assertTrue(result.isPresent());
        assertEquals("Цитата по ID", result.get().getQuote());
        verify(epigraphRepository).findById(3L);
    }

    @Test
    void getById_ShouldReturnEmptyIfNotFound() {
        when(epigraphRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Epigraph> result = epigraphService.getById(99L);

        assertTrue(result.isEmpty());
        verify(epigraphRepository).findById(99L);
    }

    @Test
    void getRandom_ShouldReturnOneOfTheList() {
        List<Epigraph> list = List.of(
                new Epigraph(1L, "Один", "А"),
                new Epigraph(2L, "Два", "Б")
        );
        when(epigraphRepository.findAll()).thenReturn(list);

        Epigraph result = epigraphService.getRandom();

        assertNotNull(result);
        assertTrue(list.contains(result));
        verify(epigraphRepository).findAll();
    }

    @Test
    void getRandom_ShouldThrowException_WhenEmpty() {
        when(epigraphRepository.findAll()).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> epigraphService.getRandom());
        assertEquals("Нет эпиграфов", exception.getMessage());
    }
}