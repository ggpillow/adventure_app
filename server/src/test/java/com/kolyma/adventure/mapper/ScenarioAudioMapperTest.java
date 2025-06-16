package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ScenarioAudioDTO;
import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.model.ScenarioAudio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioAudioMapperTest {

    @Test
    void toDTO_ShouldMapAllFields() {
        Scenario scenario = new Scenario();
        scenario.setId(5L);
        ScenarioAudio audio = new ScenarioAudio(1L, "url.mp3", scenario);

        ScenarioAudioDTO dto = ScenarioAudioMapper.toDTO(audio);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("url.mp3", dto.getAudioUrl());
        assertEquals(5L, dto.getScenarioId());
    }

    @Test
    void toDTO_ShouldHandleNullScenario() {
        ScenarioAudio audio = new ScenarioAudio(2L, "sound.mp3", null);

        ScenarioAudioDTO dto = ScenarioAudioMapper.toDTO(audio);

        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("sound.mp3", dto.getAudioUrl());
        assertNull(dto.getScenarioId());
    }

    @Test
    void toEntity_ShouldMapAllFields() {
        Scenario scenario = new Scenario();
        scenario.setId(7L);
        ScenarioAudioDTO dto = new ScenarioAudioDTO(3L, "voice.ogg", 7L);

        ScenarioAudio audio = ScenarioAudioMapper.toEntity(dto, scenario);

        assertNotNull(audio);
        assertEquals(3L, audio.getId());
        assertEquals("voice.ogg", audio.getAudioUrl());
        assertEquals(7L, audio.getScenario().getId());
    }
}