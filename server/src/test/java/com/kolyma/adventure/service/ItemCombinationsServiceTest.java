package com.kolyma.adventure.service;

import com.kolyma.adventure.model.ItemCombination;
import com.kolyma.adventure.model.Resource;
import com.kolyma.adventure.repository.ItemCombinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemCombinationServiceTest {

    @Mock
    private ItemCombinationRepository repository;

    @InjectMocks
    private ItemCombinationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnList() {
        List<ItemCombination> list = List.of(
                new ItemCombination(1L, new Resource(), new Resource(), "Результат", "img.png")
        );
        when(repository.findAll()).thenReturn(list);

        List<ItemCombination> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void findByResources_ShouldReturnCombinationIfExists() {
        ItemCombination combo = new ItemCombination(2L, new Resource(), new Resource(), "Комбинация", null);
        when(repository.findByResourcePair(1L, 2L)).thenReturn(Optional.of(combo));

        Optional<ItemCombination> result = service.findByResources(1L, 2L);

        assertTrue(result.isPresent());
        verify(repository).findByResourcePair(1L, 2L);
    }

    @Test
    void findByResources_ShouldReturnEmptyIfNotExists() {
        when(repository.findByResourcePair(1L, 2L)).thenReturn(Optional.empty());

        Optional<ItemCombination> result = service.findByResources(1L, 2L);

        assertTrue(result.isEmpty());
        verify(repository).findByResourcePair(1L, 2L);
    }
}