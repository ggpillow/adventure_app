package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.Ending;
import com.kolyma.adventure.model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EndingRepository extends JpaRepository<Ending, Long> {

    List<Ending> findByScenario_Id(Long scenarioId);
    Ending findByScenario_IdAndTitleEnding(Long scenarioId, String titleEnding);

}