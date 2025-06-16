package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.repository.ScenarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    public ScenarioService(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public List<Scenario> getAll() { //возврщает список всех сценариев из базы
        return scenarioRepository.findAll();
    }

    public Optional<Scenario> getById(Long id) { //Optional позволяет аккуратно обработать случай, когда сценарий с нужным ID отсутствует
        return scenarioRepository.findById(id);
    }
}