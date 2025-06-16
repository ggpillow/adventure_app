package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ParagraphDTO;
import com.kolyma.adventure.model.Effect;
import com.kolyma.adventure.model.Paragraph;
import com.kolyma.adventure.service.ParagraphService;
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

class ParagraphControllerTest {

    @Mock
    private ParagraphService paragraphService;

    @InjectMocks
    private ParagraphController paragraphController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllParagraphs_ReturnsOk_WhenDataExists() {
        List<Paragraph> mockList = List.of(
                new Paragraph(1L, 1, "Тестовый абзац", new Effect(1L, "Сила", "Описание", "positive"))
        );
        when(paragraphService.getAll()).thenReturn(mockList);

        ResponseEntity<List<ParagraphDTO>> response = paragraphController.getAllParagraphs();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals("Тестовый абзац", response.getBody().get(0).getParagraphDescr());
    }

    @Test
    void getAllParagraphs_ReturnsNotFound_WhenEmpty() {
        when(paragraphService.getAll()).thenReturn(List.of());

        ResponseEntity<List<ParagraphDTO>> response = paragraphController.getAllParagraphs();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getById_ReturnsOk_WhenExists() {
        Paragraph paragraph = new Paragraph(1L, 1, "Абзац по ID", null);
        when(paragraphService.getById(1L)).thenReturn(Optional.of(paragraph));

        ResponseEntity<ParagraphDTO> response = paragraphController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Абзац по ID", response.getBody().getParagraphDescr());
    }

    @Test
    void getById_ReturnsNotFound_WhenMissing() {
        when(paragraphService.getById(999L)).thenReturn(Optional.empty());

        ResponseEntity<ParagraphDTO> response = paragraphController.getById(999L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getByParagraphNumber_ReturnsOk_WhenExists() {
        Paragraph paragraph = new Paragraph(2L, 42, "Абзац номер 42", null);
        when(paragraphService.getByParagraphNumber(42)).thenReturn(Optional.of(paragraph));

        ResponseEntity<ParagraphDTO> response = paragraphController.getByParagraphNumber(42);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(42, response.getBody().getParagraphNumber());
    }

    @Test
    void getByParagraphNumber_ReturnsNotFound_WhenMissing() {
        when(paragraphService.getByParagraphNumber(999)).thenReturn(Optional.empty());

        ResponseEntity<ParagraphDTO> response = paragraphController.getByParagraphNumber(999);

        assertEquals(404, response.getStatusCodeValue());
    }
}
