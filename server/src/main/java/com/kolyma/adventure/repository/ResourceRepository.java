package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long>{
}
