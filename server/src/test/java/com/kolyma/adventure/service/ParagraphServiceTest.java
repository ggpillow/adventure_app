package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Paragraph;
import com.kolyma.adventure.repository.ParagraphRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParagraphServiceTest {

    @Mock
    private ParagraphRepository paragraphRepository;

    @InjectMocks
    private ParagraphService paragraphService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllParagraphs() {
        List<Paragraph> mockParagraphs = List.of(
                new Paragraph(1L, 1, "Paragraph 1", null),
                new Paragraph(2L, 2, "Paragraph 2", null)
        );

        when(paragraphRepository.findAll()).thenReturn(mockParagraphs);

        List<Paragraph> result = paragraphService.getAll();

        assertEquals(2, result.size());
        assertEquals("Paragraph 1", result.get(0).getParagraphDescr());
        verify(paragraphRepository).findAll();
    }

    @Test
    void getById_ShouldReturnParagraphIfExists() {
        Paragraph paragraph = new Paragraph(3L, 3, "Found by ID", null);
        when(paragraphRepository.findById(3L)).thenReturn(Optional.of(paragraph));

        Optional<Paragraph> result = paragraphService.getById(3L);

        assertTrue(result.isPresent());
        assertEquals("Found by ID", result.get().getParagraphDescr());
        verify(paragraphRepository).findById(3L);
    }

    @Test
    void getById_ShouldReturnEmptyIfNotFound() {
        when(paragraphRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Paragraph> result = paragraphService.getById(99L);

        assertTrue(result.isEmpty());
        verify(paragraphRepository).findById(99L);
    }

    @Test
    void getByParagraphNumber_ShouldReturnParagraphIfExists() {
        Paragraph paragraph = new Paragraph(4L, 44, "Found by number", null);
        when(paragraphRepository.findByParagraphNumber(44)).thenReturn(Optional.of(paragraph));

        Optional<Paragraph> result = paragraphService.getByParagraphNumber(44);

        assertTrue(result.isPresent());
        assertEquals("Found by number", result.get().getParagraphDescr());
        verify(paragraphRepository).findByParagraphNumber(44);
    }

    @Test
    void getByParagraphNumber_ShouldReturnEmptyIfNotFound() {
        when(paragraphRepository.findByParagraphNumber(404)).thenReturn(Optional.empty());

        Optional<Paragraph> result = paragraphService.getByParagraphNumber(404);

        assertTrue(result.isEmpty());
        verify(paragraphRepository).findByParagraphNumber(404);
    }
}
