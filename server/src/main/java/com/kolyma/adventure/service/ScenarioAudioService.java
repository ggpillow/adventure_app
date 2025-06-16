package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.model.ScenarioAudio;
import com.kolyma.adventure.repository.ScenarioAudioRepository;
import com.kolyma.adventure.repository.ScenarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioAudioService {

    private final ScenarioAudioRepository audioRepository;
    private final ScenarioRepository scenarioRepository;

    public ScenarioAudioService(ScenarioAudioRepository audioRepository, ScenarioRepository scenarioRepository) {
        this.audioRepository = audioRepository;
        this.scenarioRepository = scenarioRepository;
    }

    public List<ScenarioAudio> getByScenarioId(Long scenarioId) {
        return audioRepository.findByScenarioId(scenarioId);
    }

    public ScenarioAudio save(Long scenarioId, ScenarioAudio audio) {
        Scenario scenario = scenarioRepository.findById(scenarioId).orElseThrow();
        audio.setScenario(scenario);
        return audioRepository.save(audio);
    }
}