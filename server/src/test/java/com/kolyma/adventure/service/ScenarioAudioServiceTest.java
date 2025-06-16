package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.model.ScenarioAudio;
import com.kolyma.adventure.repository.ScenarioAudioRepository;
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

class ScenarioAudioServiceTest {

    @Mock
    private ScenarioAudioRepository audioRepository;

    @Mock
    private ScenarioRepository scenarioRepository;

    @InjectMocks
    private ScenarioAudioService audioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByScenarioId_ShouldReturnList() {
        List<ScenarioAudio> audios = List.of(
                new ScenarioAudio(1L, "track1.mp3", null),
                new ScenarioAudio(2L, "track2.mp3", null)
        );
        when(audioRepository.findByScenarioId(42L)).thenReturn(audios);

        List<ScenarioAudio> result = audioService.getByScenarioId(42L);

        assertEquals(2, result.size());
        assertEquals("track1.mp3", result.get(0).getAudioUrl());
        verify(audioRepository).findByScenarioId(42L);
    }

    @Test
    void save_ShouldSetScenarioAndSaveAudio() {
        Long scenarioId = 5L;
        Scenario scenario = new Scenario();
        scenario.setId(scenarioId);
        ScenarioAudio audio = new ScenarioAudio(null, "intro.mp3", null);

        when(scenarioRepository.findById(scenarioId)).thenReturn(Optional.of(scenario));
        when(audioRepository.save(any(ScenarioAudio.class))).thenAnswer(i -> i.getArgument(0));

        ScenarioAudio saved = audioService.save(scenarioId, audio);

        assertNotNull(saved);
        assertEquals("intro.mp3", saved.getAudioUrl());
        assertEquals(scenario, saved.getScenario());

        verify(scenarioRepository).findById(scenarioId);
        verify(audioRepository).save(audio);
    }

    @Test
    void save_ShouldThrowException_WhenScenarioNotFound() {
        when(scenarioRepository.findById(999L)).thenReturn(Optional.empty());

        ScenarioAudio audio = new ScenarioAudio(null, "fail.mp3", null);

        assertThrows(Exception.class, () -> audioService.save(999L, audio));
        verify(scenarioRepository).findById(999L);
        verify(audioRepository, never()).save(any());
    }
}