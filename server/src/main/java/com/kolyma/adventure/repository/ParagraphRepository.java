package com.kolyma.adventure.repository;

import com.kolyma.adventure.model.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {

    // Найти абзац с определённым номером
    Optional<Paragraph> findByParagraphNumber(int paragraphNumber);
}