package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchemeRepository  extends JpaRepository<Scheme, Long> {
    List<Scheme> findByScenario_Id(Long scenarioId);
}
