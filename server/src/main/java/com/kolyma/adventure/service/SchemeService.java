package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Scheme;
import com.kolyma.adventure.repository.SchemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchemeService {

    private final SchemeRepository schemeRepository;

    public SchemeService(SchemeRepository schemeRepository) {
        this.schemeRepository = schemeRepository;
    }

    public List<Scheme> getAll() {
        return schemeRepository.findAll();
    }

    public Optional<Scheme> getById(Long id) {
        return schemeRepository.findById(id);
    }

    public List<Scheme> getByScenarioId(Long scenarioId) {
        return schemeRepository.findByScenario_Id(scenarioId);
    }
}