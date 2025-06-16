package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.Effect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EffectRepository extends JpaRepository<Effect, Long> {
    // Дополнительно можно добавить поиск по типу или названию, если потребуется
}