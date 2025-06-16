package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ScenarioAudioDTO;
import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.model.ScenarioAudio;

public class ScenarioAudioMapper {

    public static ScenarioAudioDTO toDTO(ScenarioAudio audio) {
        return new ScenarioAudioDTO(
                audio.getId(),
                audio.getAudioUrl(),
                audio.getScenario() != null ? audio.getScenario().getId() : null
        );
    }

    public static ScenarioAudio toEntity(ScenarioAudioDTO dto, Scenario scenario) {
        ScenarioAudio audio = new ScenarioAudio();
        audio.setId(dto.getId());
        audio.setAudioUrl(dto.getAudioUrl());
        audio.setScenario(scenario);
        return audio;
    }
}