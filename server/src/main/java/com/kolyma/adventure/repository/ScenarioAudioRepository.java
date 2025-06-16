package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.ScenarioAudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioAudioRepository extends JpaRepository<ScenarioAudio, Long> {
    List<ScenarioAudio> findByScenarioId(Long scenarioId);
}