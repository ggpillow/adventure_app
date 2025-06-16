package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.Epigraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpigraphRepository extends JpaRepository<Epigraph, Long> {
}