package com.kolyma.adventure.controller;

import com.kolyma.adventure.dto.ScenarioAudioDTO;
import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.model.ScenarioAudio;
import com.kolyma.adventure.service.ScenarioAudioService;
import com.kolyma.adventure.mapper.ScenarioAudioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScenarioAudioControllerTest {

    @Mock
    private ScenarioAudioService audioService;

    @InjectMocks
    private ScenarioAudioController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByScenario_ShouldReturnOkWithList() {
        Scenario scenario = new Scenario();
        scenario.setId(10L);
        List<ScenarioAudio> mockAudios = List.of(
                new ScenarioAudio(1L, "a1.mp3", scenario),
                new ScenarioAudio(2L, "a2.mp3", scenario)
        );

        when(audioService.getByScenarioId(10L)).thenReturn(mockAudios);

        ResponseEntity<List<ScenarioAudioDTO>> response = controller.getByScenario(10L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("a1.mp3", response.getBody().get(0).getAudioUrl());
    }
}