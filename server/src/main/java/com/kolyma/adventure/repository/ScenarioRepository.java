package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
