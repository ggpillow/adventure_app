package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Ending;
import com.kolyma.adventure.repository.EndingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndingService {

    private final EndingRepository endingRepository;

    public EndingService(EndingRepository endingRepository) {
        this.endingRepository = endingRepository;
    }

    // Получить все концовки по ID сценария
    public List<Ending> getByScenarioId(Long scenarioId) {
        return endingRepository.findByScenario_Id(scenarioId);
    }

    // Получить конкретную концовку по ID сценария и названию концовки
    public Ending getByScenarioIdAndTitle(Long scenarioId, String titleEnding) {
        Optional<Ending> optionalEnding = Optional.ofNullable(
                endingRepository.findByScenario_IdAndTitleEnding(scenarioId, titleEnding)
        );

        return optionalEnding.orElseThrow(() ->
                new RuntimeException("Концовка с названием '" + titleEnding + "' для сценария " + scenarioId + " не найдена.")
        );
    }

    public List<Ending> getAll() {
        return endingRepository.findAll();
    }
}
