package com.kolyma.adventure.service;

import com.kolyma.adventure.dto.ResourceDTO;
import com.kolyma.adventure.mapper.ResourceMapper;
import com.kolyma.adventure.model.Resource;
import com.kolyma.adventure.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceServiceTest {

    @Mock
    private ResourceRepository repository;

    @InjectMocks
    private ResourceService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnListOfDTOs() {
        List<Resource> mockResources = List.of(
                new Resource(1L, "Вода"),
                new Resource(2L, "Огонь")
        );

        when(repository.findAll()).thenReturn(mockResources);

        List<ResourceDTO> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("Вода", result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void getById_ShouldReturnResource_WhenExists() {
        Resource resource = new Resource(3L, "Воздух");

        when(repository.findById(3L)).thenReturn(Optional.of(resource));

        Resource result = service.getById(3L);

        assertNotNull(result);
        assertEquals("Воздух", result.getName());
        verify(repository).findById(3L);
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getById(99L));
        assertEquals("Ресурс не найден: id = 99", ex.getMessage());
        verify(repository).findById(99L);
    }
}