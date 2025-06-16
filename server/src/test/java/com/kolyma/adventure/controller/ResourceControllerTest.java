package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ResourceDTO;
import com.kolyma.adventure.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnOkWithList() {
        List<ResourceDTO> mockList = List.of(
                new ResourceDTO(1L, "Земля"),
                new ResourceDTO(2L, "Эфир")
        );

        when(resourceService.getAll()).thenReturn(mockList);

        ResponseEntity<List<ResourceDTO>> response = resourceController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Земля", response.getBody().get(0).getName());
    }
}